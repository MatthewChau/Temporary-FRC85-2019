/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.sensors.ProxSensors;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Variables {
    //Lift Vertical Postitions
    public static final int HATCH_LOW = 10;
    public static final int HATCH_MIDDLE = 100;
    public static final int HATCH_HIGH = 1000;
    public static final int HATCH_LOADING_STATION = 15;
    public static final int HATCH_FLOOR = 5;
    public static final int HATCH_DROP = 5;
    
    public static final int CARGO_LOW = 20;
    public static final int CARGO_MIDDLE = 120;
    public static final int CARGO_HIGH = 1200;
    public static final int CARGO_LOADING_STATION = 95;
    public static final int CARGO_FLOOR = 5;

    //Lift Horizontal Positions
    public static int platform = 0;
    public static int bumpers = 0;
    public static int CENTER_DRIVE = 0;
    public static int CLIMB_HAB = 0;

    public static final int HORIZONTAL_PROTECTED = 7;
    public static final int HORIZONTAL_UNPROTECTED = 0;
    public static final int BUMPERS_TWO = 3;
    public static final int range = 0;

    //Intake Positions and Degrees
    public static final int INTAKE_DEGREE_ONE = 0;
    public static final int INTAKE_DEGREE_TWO = 90;
    public static final int INTAKE_DEGREE_THREE = 95;

    private static Variables _instance;

    public final double DEADBAND = 0.05;

    private Variables() {


    }

    public static Variables getInstance() {
        if (_instance == null) {
			_instance = new Variables();
        }
        return _instance;
    }

    public void outputVariables() {
        SmartDashboard.putNumber("Left Front Percent", DriveTrain.getInstance().getLeftFrontPercent());
        SmartDashboard.putNumber("Left Back Percent", DriveTrain.getInstance().getLeftBackPercent());
        SmartDashboard.putNumber("Right Front Percent", DriveTrain.getInstance().getRightFrontPercent());
        SmartDashboard.putNumber("Right Back Percent", DriveTrain.getInstance().getRightBackPercent());

        SmartDashboard.putNumber("Distance", Vision.distance());

        SmartDashboard.putNumber("Fused", IMU.getInstance().getFusedHeading());

        SmartDashboard.putNumber("Initial Yaw", IMU.getInstance().getInitialYaw());
        SmartDashboard.putNumber("Yaw", IMU.getInstance().getYaw());

        SmartDashboard.putNumber("Initial Pitch", IMU.getInstance().getInitialPitch());
        SmartDashboard.putNumber("Pitch", IMU.getInstance().getPitch());

        SmartDashboard.putNumber("Initial Roll", IMU.getInstance().getInitialRoll());
        SmartDashboard.putNumber("Roll", IMU.getInstance().getRoll());
//        SmartDashboard.putBoolean("Prox me OwO", ProxSensors.getInstance().getTopLimit());
    }

}