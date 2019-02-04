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
import frc.robot.sensors.IMU;
import frc.robot.sensors.ProxSensors;
import frc.robot.subsystems.DriveTrain;

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
            out.append("Time,Match Time,"
                  + "Left Front Motor,Left Back Motor,Right Front Motor,Right Back Motor,"
                  + "Lift Top Limit,Lift Bottom Limit,Lift Front Limit,Lift Rear Limit,"
                  + "Intake Top Limit,Intake Bottom Limit,"
                  + "Yaw,Pitch,Roll,"
                  + ",");
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
            
            String leftFrontMotor = Double.toString(DriveTrain.getInstance().getLeftFrontPercent());
            String leftBackMotor = Double.toString(DriveTrain.getInstance().getLeftBackPercent());
            String rightFrontMotor = Double.toString(DriveTrain.getInstance().getRightFrontPercent());
            String rightBackMotor = Double.toString(DriveTrain.getInstance().getRightBackPercent());

            String liftTopLimit = Boolean.toString(ProxSensors.getInstance().getLiftTopLimit());
            String liftBottomLimit = Boolean.toString(ProxSensors.getInstance().getLiftBottomLimit());
            String liftFrontLimit = Boolean.toString(ProxSensors.getInstance().getLiftFrontLimit());
            String liftRearLimit = Boolean.toString(ProxSensors.getInstance().getLiftRearLimit());

            String intakeTopLimit = Boolean.toString(ProxSensors.getInstance().getIntakeTopLimit());
            String intakeBottomLimit = Boolean.toString(ProxSensors.getInstance().getIntakeBottomLimit());

            String yaw = Double.toString(IMU.getInstance().getYaw());
            String pitch = Double.toString(IMU.getInstance().getPitch());
            String roll = Double.toString(IMU.getInstance().getRoll());


            out.append(time + "," + matchTime + "," 
                    + leftFrontMotor + "," + leftBackMotor + "," + rightFrontMotor + "," + rightBackMotor + ","
                    + liftTopLimit + "," + liftBottomLimit + "," + liftFrontLimit + "," + liftRearLimit + ","
                    + intakeTopLimit + "," + intakeBottomLimit + ","
                    + yaw + "," + pitch + "," + roll + ","
                    + ",");

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