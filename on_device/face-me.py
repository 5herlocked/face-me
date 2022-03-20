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

import Jetson.GPIO as GPIO

from dotenv import load_dotenv
from multiprocessing import Queue
from uuid import uuid4
from awscrt import io, mqtt, auth, http
from awsiot import mqtt_connection_builder
from botocore.exceptions import ClientError

load_dotenv()

iot_endpoint = os.environ.get("iot_endpoint")
bucket_name = os.environ.get("bucket_name")

s3_client = boto3.client('s3', 'us-east-1')

event_queue = Queue()
message_queue = Queue()
stop = False

def interrupt_handler(sig, frame):
    # CLEAN UP
    # Disconnect
    global stop
    stop = True
    print("Disconnecting...")
    disconnect_future = mqtt_connection.disconnect()
    disconnect_future.result()
    print("Disconnected!")
    sys.exit(0)


if __name__ == '__main__':
    pass
    

# def gstreamer_pipeline(
#         sensor_id=0,
#         capture_width=1920,
#         capture_height=1080,
#         display_width=960,
#         display_height=540,
#         framerate=30,
#         flip_method=0,
# ):
#     return (
#             "nvarguscamerasrc sensor-id=%d !"
#             "video/x-raw(memory:NVMM), width=(int)%d, height=(int)%d, framerate=(fraction)%d/1 ! "
#             "nvvidconv flip-method=%d ! "
#             "video/x-raw, width=(int)%d, height=(int)%d, format=(string)BGRx ! "
#             "videoconvert ! "
#             "video/x-raw, format=(string)BGR ! appsink"
#             % (
#                 sensor_id,
#                 capture_width,
#                 capture_height,
#                 framerate,
#                 flip_method,
#                 display_width,
#                 display_height,
#             )
#     )