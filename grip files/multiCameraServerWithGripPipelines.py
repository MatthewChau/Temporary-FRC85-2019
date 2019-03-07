#!/usr/bin/env python3

import json
import time
import sys
import multiprocessing
import numpy
import cscore
import cv2

from networktables import NetworkTablesInstance
from toppipeline import TopPipeline
from bottompipeline import BottomPipeline

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

def getFrame(sink, img, table):
    if sink.getSource().isConnected:
        timestamp, img = topSink.grabFrame(img)
        table.getEntry("Timestamp").setString(timestamp)
        if timestamp == 0:
            errorMessage = '{} error: {}'.format(sink.getSource().getName(), sink.getError())
            print(errorMessage)
            table.getEntry("Error").setString(errorMessage)
            return timestamp, img

        table.getEntry("Error").setString("")
        return timestamp, img

def startPipeline(pipeline, img, timestamp):
    try:
        pipeline.process(img)
        table.getEntry("ProcessedTimestamp").setString(timestamp)
        table.getEntry("PipelineError").setString("")
    except ValueError as err:
        table.getEntry("PipelineError").setString(err)
    except:
        table.getEntry("PipelineError").setString(sys.exc_info()[0])

if __name__ == "__main__":
    # start NetworkTables
    ntinst = NetworkTablesInstance.getDefault()
    ntinst.startClientTeam(85)
    table = ntinst.getTable("Vision")
    topTable = table.getSubTable("Top")
    bottomTable = table.getSubTable("Bottom")

    topCamera = startCamera("top", "/dev/video0", None)
    bottomCamera = startCamera("bottom", "/dev/video1", None)
    driverAssistCamera = startCamera("driver_assist", "/dev/video2", None)

    topSink = cs.getVideo(camera=topCamera)
    bottomSink = cs.getVideo(camera=bottomCamera)
    
    topImg = numpy.zeros(shape=(height, width, 3), dtype=numpy.uint8)
    bottomImg = numpy.zeros(shape=(height, width, 3), dtype=numpy.uint8)

    topPipeline = TopPipeline()
    bottomPipeline = BottomPipeline()

    while True:
        topTimestamp, topImg = getFrame(topSink, topImg, topTable)
        bottomTimestamp, bottomImg = getFrame(bottomSink, bottomImg, bottomTable)
   
        topProcess = multiprocessing.Process(target=startPipeline, args=(topPipeline, topImg, topTimestamp))
        bottomProcess = multiprocessing.Process(target=startPipeline, args=(bottomPipeline, bottomImg, bottomTimestamp))

        topProcess.start()
        bottomProcess.start()

        topTable.getEntry("Value").setString(topPipeline.convex_hulls_output)
        bottomTable.getEntry("Value").setString(bottomPipeline.filter_lines_output)

        time.sleep(0.01)
