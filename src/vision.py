from cscore import CameraServer
from networktables import NetworkTables
import time
import logging

import cv2
import numpy as np


NetworkTables.initialize(server='roborio-2035-frc.local')

rpi = NetworkTables.getTable("RPi")


logging.basicConfig(level=logging.DEBUG)
cs = CameraServer.getInstance()
cs.enableLogging()
camera = cs.startAutomaticCapture()
camera.setResolution(160, 120)

sink = cs.getVideo()

img = np.zeros(shape=(240, 320, 3),dtype=np.uint8)
output = cs.putVideo("Name", 320, 240)
capture = cv2.VideoCapture('http://10.20.35.210:5800/image.mjpg')
while True:
    ret, input_img = capture.read() 
    
    hsv_img = cv2.cvtColor(input_img, cv2.COLOR_BGR2HSV)
    binary_img = cv2.inRange(hsv_img, (71,119,138), (102,227,255))
    _, contours, _ = cv2.findContours(binary_img,cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    if (len(contours) > 0):
        largest = contours[0]
        for contour in contours:
            if cv2.contourArea(contour) > cv2.contourArea(largest):
                largest = contour
        rect = cv2.minAreaRect(largest)
        center, _, _ = rect
        center_x, center_y = center
        rpi.putNumber('tx', ((center_x/150.0)-0.5)*2.0)
        rpi.putNumber('ty', ((center_y/108.0)-0.5)*2.0)
        rpi.putNumber('ta', np.sum(binary_img == 255)/(160.0*120.0))
    output.putFrame(input_img)
capture.release()