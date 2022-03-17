from datetime import time

import cv2
from imutils.video import VideoStream
import imutils

class Camera:
    def __init__(self, tx_queue, rx_queue):
        self.vs = VideoStream(src=0).start()
        time.sleep(1.0)

        self.tx_queue = tx_queue
        self.rx_queue = rx_queue
        pass

    def start_observing(self):
        pass

    def stop_observing(self):
        pass

    def release_camera_lock(self):
        pass
    