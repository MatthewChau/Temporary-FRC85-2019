#!/usr/bin/env python3
#----------------------------------------------------------------------------
# Copyright (c) 2018 FIRST. All Rights Reserved.
# Open Source Software - may be modified and shared by FRC teams. The code
# must be accompanied by the FIRST BSD license file in the root directory of
# the project.
#----------------------------------------------------------------------------

import json
import time
import sys
import multiprocessing
import numpy
import cscore
import cv2

#from cscore import CameraServer, VideoSource, UsbCamera, MjpegServer, CvSink
from networktables import NetworkTablesInstance
from toppipeline import TopPipeline
from bottompipeline import BottomPipeline

#   JSON format:
#   {
#       "team": <team number>,
#       "ntmode": <"client" or "server", "client" if unspecified>
#       "cameras": [
#           {
#               "name": <camera name>
#               "path": <path, e.g. "/dev/video0">
#               "pixel format": <"MJPEG", "YUYV", etc>   // optional
#               "width": <video mode width>              // optional
#               "height": <video mode height>            // optional
#               "fps": <video mode fps>                  // optional
#               "brightness": <percentage brightness>    // optional
#               "white balance": <"auto", "hold", value> // optional
#               "exposure": <"auto", "hold", value>      // optional
#               "properties": [                          // optional
#                   {
#                       "name": <property name>
#                       "value": <property value>
#                   }
#               ],
#               "stream": {                              // optional
#                   "properties": [
#                       {
#                           "name": <stream property name>
#                           "value": <stream property value>
#                       }
#                   ]
#               }
#           }
#       ]
#   }

width=320
height=240
frames_per_sec=15
cs = cscore.CameraServer.getInstance()

def startCamera(name, path, configJson):
    print("Starting camera '{}' on {}".format(name, path))
    camera = cscore.UsbCamera(name, path)
    camera.setVideoMode(cscore.VideoMode.PixelFormat.kMJPEG, width, height, frames_per_sec)

    if configJson is not None:
        camera.setConfigJson(configJson)

    cs.startAutomaticCapture(camera=camera)
    camera.setConnectionStrategy(cscore.VideoSource.ConnectionStrategy.kKeepOpen)

    return camera 

def getFrame(sink, img):
    if sink.getSource().isConnected:
        timestamp, img = topSink.grabFrame(img)
        if timestamp == 0:
            print('{} error: {}'.format(sink.getSource().getName(), sink.getError()))
            return

        return img


def startPipeline(pipeline, img, entry):
    result = pipeline.process(img)
    if result is not None:
        entry.setRaw(result)


if __name__ == "__main__":
    # start NetworkTables
    ntinst = NetworkTablesInstance.getDefault()
    ntinst.startClientTeam(85)
    table = ntinst.getTable("Vision")
    topEntry = table.getEntry("top")
    bottomEntry = table.getEntry("bottom")

    #outputStream = cs.putVideo("Output", width, height)
    #mjpegServer = cscore.MjpegServer("httpserver", 8081)

    topCamera = startCamera("top", "/dev/video0", None)
    bottomCamera = startCamera("bottom", "/dev/video1", None)

    #mjpegServer.setSource(topCamera)
    
    topSink = cs.getVideo(camera=topCamera)
    bottomSink = cs.getVideo(camera=bottomCamera)
    
    topImg = numpy.zeros(shape=(height, width, 3), dtype=numpy.uint8)
    bottomImg = numpy.zeros(shape=(height, width, 3), dtype=numpy.uint8)

    topPipeline = TopPipeline()
    bottomPipeline = BottomPipeline()

    #startPipeline(topSink, topPipeline)
    while True:
        topImg = getFrame(topSink, topImg)
        bottomImg = getFrame(bottomSink, bottomImg)
   
        topProcess = multiprocessing.Process(target=startPipeline, args=(topPipeline, topImg, topEntry))
        bottomProcess = multiprocessing.Process(target=startPipeline, args=(bottomPipeline, bottomImg, bottomEntry))

        topProcess.start()
        bottomProcess.start()

        #topProcess.join()
        #bottomProcess.join()
        time.sleep(0.01)

