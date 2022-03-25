import argparse
import json
import os
import logging
import random
import signal
import sys
import threading
import cv2
import boto3

from multiprocessing import Queue
from uuid import uuid4
from awscrt import io, mqtt, auth, http
from awsiot import mqtt_connection_builder
from botocore.exceptions import ClientError
from prompt_toolkit import prompt

iot_endpoint = 'a2lbvrveroeze3-ats.iot.us-east-1.amazonaws.com'
bucket_name = 'jetson-ingestion-bucket'

s3_client = boto3.client('s3', 'us-east-1')

message_queue = Queue()

event_loop_group = io.EventLoopGroup(1)
host_resolver = io.DefaultHostResolver(event_loop_group)
client_bootstrap = io.ClientBootstrap(event_loop_group, host_resolver)

kill_message = ("q", "q", "q")


def interrupt_handler(sig, frame):
    # CLEAN UP
    # Disconnect
    message_queue.put(kill_message)
    messages_thread.join()
    cam.release()
    print("Disconnecting...")
    disconnect_future = mqtt_connection.disconnect()
    disconnect_future.result()
    print("Disconnected!")
    sys.exit(0)


# Callback when connection is accidentally lost.
def on_connection_interrupted(connection, error, **kwargs):
    print("Connection interrupted. error: {}".format(error))


# Callback when an interrupted connection is re-established.
def on_connection_resumed(connection, return_code, session_present, **kwargs):
    print("Connection resumed. return_code: {} session_present: {}".format(return_code, session_present))

    if return_code == mqtt.ConnectReturnCode.ACCEPTED and not session_present:
        print("Session did not persist. Resubscribing to existing topics...")
        resubscribe_future, _ = connection.resubscribe_existing_topics()

        # Cannot synchronously wait for resubscribe result because we're on the connection's event-loop thread,
        # evaluate result with a callback instead.
        resubscribe_future.add_done_callback(on_resubscribe_complete)


def on_resubscribe_complete(resubscribe_future):
    resubscribe_results = resubscribe_future.result()
    print("Resubscribe results: {}".format(resubscribe_results))

    for topic, qos in resubscribe_results['topics']:
        if qos is None:
            sys.exit("Server rejected resubscribe to topic: {}".format(topic))


# Callback when the subscribed topic receives a message
# Check if it's an AUTH or NAUTH message and then just print that
def on_message_received(topic, payload, dup, qos, retain, **kwargs):
    # print('Received message from topic {}: {}'.format(topic, payload))
    payload = json.loads(payload)
    if payload['auth'] == 'True':
        print('Door Unlocked')
    else:
        print('Door Locked')


def build_mqtt():
    proxy_options = None
    if args.proxy_host:
        proxy_options = http.HttpProxyOptions(host_name=args.proxy_host, port=args.proxy_port)

    if args.use_websocket:
        credentials_provider = auth.AwsCredentialsProvider.new_default_chain(client_bootstrap)
        connect_builder = mqtt_connection_builder.websockets_with_default_aws_signing(
            endpoint=iot_endpoint,
            client_bootstrap=client_bootstrap,
            region=args.signing_region,
            credentials_provider=credentials_provider,
            http_proxy_options=proxy_options,
            ca_filepath=args.root_ca,
            on_connection_interrupted=on_connection_interrupted,
            on_connection_resumed=on_connection_resumed,
            client_id=args.client_id,
            clean_session=False,
            keep_alive_secs=30
        )

    else:
        connect_builder = mqtt_connection_builder.mtls_from_path(
            endpoint=iot_endpoint,
            port=args.port,
            cert_filepath=args.cert,
            pri_key_filepath=args.key,
            client_bootstrap=client_bootstrap,
            ca_filepath=args.root_ca,
            on_connection_interrupted=on_connection_interrupted,
            on_connection_resumed=on_connection_resumed,
            client_id=args.client_id,
            clean_session=False,
            keep_alive_secs=30,
            http_proxy_options=proxy_options
        )

    return connect_builder


def gstreamer_pipeline(
        sensor_id=0,
        capture_width=1920,
        capture_height=1080,
        display_width=960,
        display_height=540,
        framerate=30,
        flip_method=0,
):
    return (
            "nvarguscamerasrc sensor-id=%d !"
            "video/x-raw(memory:NVMM), width=(int)%d, height=(int)%d, framerate=(fraction)%d/1 ! "
            "nvvidconv flip-method=%d ! "
            "video/x-raw, width=(int)%d, height=(int)%d, format=(string)BGRx ! "
            "videoconvert ! "
            "video/x-raw, format=(string)BGR ! appsink"
            % (
                sensor_id,
                capture_width,
                capture_height,
                framerate,
                flip_method,
                display_width,
                display_height,
            )
    )


def run_ui():
    while True:
        user_id = prompt("Enter User ID: ")
        
        ret, frame = cam.read()

        if user_id == 'q':
            cam.release()
            message_queue.put(kill_message)
            return

        if not ret:
            print("failed to grab frame")
            break
        else:
            print("Image Captured")
            img_name = str(random.randint(1000000, 9999999)) + ".png"
            cv2.imwrite(img_name, frame)
            try:
                s3_key = str(user_id + "/" + img_name)
                response = s3_client.upload_file(img_name, bucket_name, s3_key)

                message_queue.put((user_id, img_name, s3_key))
                print("Image Uploaded")
                os.remove(img_name)
            except ClientError as e:
                logging.error(e)
                break


def send_messages():
    while True:
        if message_queue is not None:
            # Send message over mqtt link on specific channel
            user_id, img_name, s3_key = message_queue.get()
            if user_id == "q" or img_name == "q" or s3_key == "q":
                return

            message = {
                "uuid": user_id,
                "img_name": img_name,
                "s3_key": s3_key
            }

            mqtt_connection.publish('auth/requests', payload=json.dumps(message), qos=mqtt.QoS.AT_LEAST_ONCE)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="Send and receive messages through and MQTT connection.")
    parser.add_argument('--port', type=int, help="Specify port. AWS IoT supports 443 and 8883.")
    parser.add_argument('--cert', help="File path to your client certificate, in PEM format.")
    parser.add_argument('--key', help="File path to your private key, in PEM format.")
    parser.add_argument('--root-ca', help="File path to root certificate authority, in PEM format. " +
                                          "Necessary if MQTT server uses a certificate that's not already in " +
                                          "your trust store.")
    parser.add_argument('--client-id', default="test-" + str(uuid4()), help="Client ID for MQTT connection.")
    parser.add_argument('--topic', default="test/topic", help="Topic to subscribe to, and publish messages to.")
    parser.add_argument('--message', default="Hello World!", help="Message to publish. " +
                                                                  "Specify empty string to publish nothing.")
    parser.add_argument('--count', default=10, type=int, help="Number of messages to publish/receive before exiting. " +
                                                              "Specify 0 to run forever.")
    parser.add_argument('--use-websocket', default=False, action='store_true',
                        help="To use a websocket instead of raw mqtt. If you " +
                             "specify this option you must specify a region for signing.")
    parser.add_argument('--signing-region', default='us-east-1', help="If you specify --use-web-socket, this " +
                                                                      "is the region that will be used for computing the"
                                                                      " Sigv4 signature")
    parser.add_argument('--proxy-host', help="Hostname of proxy to connect to.")
    parser.add_argument('--proxy-port', type=int, default=8080, help="Port of proxy to connect to.")
    parser.add_argument('--verbosity', choices=[x.name for x in io.LogLevel], default=io.LogLevel.NoLogs.name,
                        help='Logging level')

    # Using globals to simplify sample code
    args = parser.parse_args()

    mqtt_connection = build_mqtt()

    print("Connecting to {} with client ID '{}'...".format(
        iot_endpoint, args.client_id))

    connect_future = mqtt_connection.connect()

    # Future.result() waits until a result is available
    connect_future.result()
    print("Connected!")

    print('Subscribing to topic')
    subscribe_future, packet_id = mqtt_connection.subscribe(
        topic='auth/verify',
        qos=mqtt.QoS.AT_LEAST_ONCE,
        callback=on_message_received
    )
    subscribe_result = subscribe_future.result()
    print("Subscribed with {}".format(str(subscribe_result['qos'])))

    # cam = cv2.VideoCapture(gstreamer_pipeline(flip_method=0), cv2.CAP_GSTREAMER)
    cam = cv2.VideoCapture(0)

    signal.signal(signal.SIGINT, interrupt_handler)

    messages_thread = threading.Thread(target=send_messages)
    messages_thread.start()

    run_ui()
    messages_thread.join()
