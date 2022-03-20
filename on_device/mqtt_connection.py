import boto3

from uuid import uuid4
from awscrt import io, mqtt, auth, http
from awsiot import mqtt_connection_builder
from botocore.exceptions import ClientError

class Connection:
    def __init__(self, iot_endpoint, message_queue):
        self.message_queue = message_queue
        self.event_loop_group = io.EventLoopGroup(1)
        self.host_resolver = io.DefaultHostResolver(event_loop_group)
        self.client_bootstrap = io.ClientBootstrap(event_loop_group, host_resolver)

    # Callback when connection is accidentally lost.
    def on_connection_interrupted(self, connection, error, **kwargs):
        print("Connection interrupted. error: {}".format(error))


    # Callback when an interrupted connection is re-established.
    def on_connection_resumed(self, connection, return_code, session_present, **kwargs):
        print("Connection resumed. return_code: {} session_present: {}".format(return_code, session_present))

        if return_code == mqtt.ConnectReturnCode.ACCEPTED and not session_present:
            print("Session did not persist. Resubscribing to existing topics...")
            resubscribe_future, _ = connection.resubscribe_existing_topics()

            # Cannot synchronously wait for resubscribe result because we're on the connection's event-loop thread,
            # evaluate result with a callback instead.
            resubscribe_future.add_done_callback(on_resubscribe_complete)


    def on_resubscribe_complete(self, resubscribe_future):
        resubscribe_results = resubscribe_future.result()
        print("Resubscribe results: {}".format(resubscribe_results))

        for topic, qos in resubscribe_results['topics']:
            if qos is None:
                sys.exit("Server rejected resubscribe to topic: {}".format(topic))


    # Callback when the subscribed topic receives a message
    # Check if it's an AUTH or NAUTH message and then just print that
    def on_message_received(self, topic, payload, dup, qos, retain, **kwargs):
        # print('Received message from topic {}: {}'.format(topic, payload))
        payload = json.loads(payload)
        if payload['auth'] == 'True':
            print('Door Unlocked')
        else:
            print('Door Locked')


    def build_mqtt(self):
        proxy_options = None
        if args.proxy_host:
            self.proxy_options = http.HttpProxyOptions(host_name=args.proxy_host, port=args.proxy_port)

        if args.use_websocket:
            self.credentials_provider = auth.AwsCredentialsProvider.new_default_chain(client_bootstrap)
            self.connect_builder = mqtt_connection_builder.websockets_with_default_aws_signing(
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
            self.connect_builder = mqtt_connection_builder.mtls_from_path(
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
