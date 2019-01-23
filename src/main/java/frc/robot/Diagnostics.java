/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import edu.wpi.first.wpilibj.DriverStation;

public class Diagnostics {

    File log;
    BufferedWriter out = null;

    private int _placeHolder = 0;

    public void init() {
        try {
            close();

            _placeHolder = 0;

            String date = new SimpleDateFormat("yyyy-MM-ddy HHmmss")
                    .format(new java.util.Date(System.currentTimeMillis()));
            log = new File("home/lvuser/log " + date + ".csv");
            log.createNewFile();
            out = new BufferedWriter(new FileWriter(log, true));
            out.append("Time,Match Time");
            out.newLine();
        } catch (Exception ex) {
            System.out.println("Error creating log file: " + ex.toString());
        }
    }

    /**
     * runs repeatedly to get values and such
     */
    public void log() {
        try{
            if (out == null) {
                init();
            }
            _placeHolder++;
            String time = Integer.toString(_placeHolder);
            String matchTime = Double.toString(DriverStation.getInstance().getMatchTime());

            //plus otros gets

            out.append(time + "," + matchTime);

            out.newLine();
        } catch (Exception ex) {
            System.out.println("Error writing diagnostic log: " + ex.toString());
        }

    }

    public void close() {
        if (out != null) {
            try {
                out.close();
                out = null;
            } catch (Exception ex) {
                System.out.println("Error closing file: " + ex.toString());
            }
        }
    }
}