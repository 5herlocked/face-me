import time
import cv2
from imutils.video import VideoStream
import threading

class Camera:
    def __init__(self, event_queue):
        self.bg_sub_thread = None
        self.vs = VideoStream(src=0)
        # synch window
        time.sleep(0.5)

        self.event_queue = event_queue

    def observe(self):
        self.vs.start()

        if not self.vs.read():
            print("VideoStream read failed")
            exit(-1)

        # motion detection code here made it object bound, so it's easier to shut down
        self.bg_sub_thread = threading.Thread(target=motion_detection, args=(self.vs, self.event_queue))
        self.bg_sub_thread.start()

        pass

    def stop(self):
        self.vs.stop()
        pass


def motion_detection(video_stream, event_queue):
    back_sub = cv2.createBackgroundSubtractorKNN()
    motion_detected =  False
    while not motion_detected:
        frame = video_stream.read()
        if frame is None:
            print("Video Stream failed to return frame.  Terminating motion detection algorithm")
            break

        fgMask = back_sub.apply(frame)

        # if foreground mask is less than x pixels, ignore it
        # else break and send to event queue.
    pass