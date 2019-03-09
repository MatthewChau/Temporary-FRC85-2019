/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Elevator;
import frc.robot.sensors.ProxSensors;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Variables {

    private static Variables _instance;
    
    // DRIVETRAIN

    public static final double DEADBAND_DRIVERSTICK = 0.1;
    public static final double DEADBAND_Z_DRIVERSTICK = 0.3;  
    public static final double DEADBAND_OPERATORSTICK = 0.05;
    public static final double TOLERANCE_ANGLE = 5.0;
    public static final int MAX_TURNS = 7; // if we go over 2520 degrees in either direction in one match then help.
    public static double A_POLYNOMIAL = .60929309069026; // polynomial stuff
    public static double B_POLYNOMIAL = 0;
    public static double C_POLYNOMIAL = .377931548393;
    public static double D_POLYNOMIAL = 0;

    // PID

    private static final double kP_ELEVATOR = 0.0004, kI_ELEVATOR = 0.00000004, kD_ELEVATOR = 0.0009;

    private static final double kP_MAST = 0.025, kI_MAST = 0.0, kD_MAST = 0.0;

    private static final double kP_INTAKE = 0.001, kI_INTAKE = 0.0, kD_INTAKE = 0.0;

    private static final double kP_DRIVE = 0.05, kI_DRIVE = 0.0, kD_DRIVE = 0.0;

    private static final double kP_VISION = 0.05, kI_VISION = 0.0, kD_VISION = 0.0;

    private static final double kP_VISION_ROT = 0.05, kI_VISION_ROT = 0.0, kD_VISION_ROT = 0.0;

    // ANGLES FOR VISION

    public static final double ROT_POS_1 = 90;
    public static final double ROT_POS_2 = -90;
    public static final double ROT_POS_3 = 60;
    public static final double ROT_POS_4 = 120;

    // ELEVATOR

    // elevator positions
    public static final int HATCH_ONE = 1565;
    public static final int HATCH_TWO = 8850;
    public static final int HATCH_THREE = 17097;
    public static final int HATCH_FLOOR = 100;
    
    public static final int CARGO_ONE = 5300;
    public static final int CARGO_SHIP = 12040;
    public static final int CARGO_TWO = 13540;
    public static final int CARGO_THREE = 21612;
    public static final int CARGO_FLOOR = 1500;

    //Elevator position limits
    public static final int ELEVATOR_MAX_POS = 21500;
    public static final int ELEVATOR_MIN_POS_MAST_PROTECTED = 2500;
    public static final int ELEVATOR_MIN_POS_MAST_FORWARD_CARGO = 1100;
    public static final int ELEVATOR_MIN_POS_MAST_FORWARD_HATCH = 0;

    //Elevator Speed Limits 
    public static final double ELEVATOR_MAX_SPEED = .5;
    public static final double ELEVATOR_MIN_SPEED = -.2;

    //Servo Angles for LiftLock
    public static final double ELEVATOR_LOCKED = 70;
    public static final double ELEVATOR_UNLOCKED = 180;

    // MAST

    //Mast Positions
    public static final int MAST_FORWARD_POS = 700000;

    //Mast position limits
    public static final int MAST_MIN_POS = 0;
    public static final int MAST_BREAKPOINT = 400000;
    public static final int MAST_MAX_POS = 871000;

    //Mast speed limits
    public static final double MAST_MAX_SPEED = .5;
    public static final double MAST_MIN_SPEED = -.5;

    // WRIST

    // wrist positions
    public static final int WRIST_ANGLE_FOR_CARGO = -767000;
    public static final int WRIST_POS_FLOOR_PICKUP = -1000000;
    public static final int WRIST_0 = 0;
    public static final int WRIST_30 = -333333;
    public static final int WRIST_45 = -500000;
    public static final int WRIST_60 = -666666;
    public static final int WRIST_90 = -908000;

    //Wrist position limits
    public static final int WRIST_MAX_POS = 0;
    public static final int WRIST_MIN_POS_MAST_BACK = -200000;
    public static final int WRIST_MIN_POS = -1124000;

    // wrist speed limits
    public static final double WRIST_MAX_SPEED_UP = 0.8;
    public static final double WRIST_MAX_SPEED_DOWN = -0.8;

    /**
     * Put variables here that should be changeable on the fly.
     */
    private Variables() {
        SmartDashboard.putNumber("kP_VLIFT", kP_ELEVATOR);
        SmartDashboard.putNumber("kI_VLIFT", kI_ELEVATOR);
        SmartDashboard.putNumber("kD_VLIFT", kD_ELEVATOR);

        SmartDashboard.putNumber("kP_HLIFT", kP_MAST);
        SmartDashboard.putNumber("kI_HLIFT", kI_MAST);
        SmartDashboard.putNumber("kD_HLIFT", kD_MAST);
        
        SmartDashboard.putNumber("kP_INTAKE", kP_INTAKE);
        SmartDashboard.putNumber("kI_INTAKE", kI_INTAKE);
        SmartDashboard.putNumber("kD_INTAKE", kD_INTAKE);

        SmartDashboard.putNumber("kP_DRIVE", kP_DRIVE);
        SmartDashboard.putNumber("kI_DRIVE", kI_DRIVE);
        SmartDashboard.putNumber("kD_DRIVE", kD_DRIVE);

        SmartDashboard.putNumber("kP_VISION", kP_VISION);
        SmartDashboard.putNumber("kI_VISION", kI_VISION);
        SmartDashboard.putNumber("kD_VISION", kD_VISION);

        SmartDashboard.putNumber("kP_VISION_ROT", kP_VISION_ROT);
        SmartDashboard.putNumber("kI_VISION_ROT", kI_VISION_ROT);
        SmartDashboard.putNumber("kD_VISION_ROT", kD_VISION_ROT);

        SmartDashboard.putNumber("MAX_SPEED_UP_INTAKE", WRIST_MAX_SPEED_UP);
        SmartDashboard.putNumber("MAX_SPEED_DOWN_INTAKE", WRIST_MAX_SPEED_DOWN);

        SmartDashboard.putNumber("UNLOCKED", ELEVATOR_UNLOCKED);
        SmartDashboard.putNumber("LOCKED", ELEVATOR_LOCKED);

        SmartDashboard.putBoolean("Joysticks Enabled", true);

        SmartDashboard.putBoolean("Disable Intake Top Limit", true);
        SmartDashboard.putBoolean("Disable Intake Prox Limit", false);

        SmartDashboard.putBoolean("Disable Intake Soft Limits", true);
        SmartDashboard.putBoolean("Disable Mast Soft Limits", true);
        SmartDashboard.putBoolean("Disable Elevator Soft Limits", true);
    }

    public static Variables getInstance() {
        if (_instance == null) {
			_instance = new Variables();
        }
        return _instance;
    }

    /**
     * get methods for changable variables
     */
    public double getElevatorKP() {
        return SmartDashboard.getNumber("kP_VLIFT", kP_ELEVATOR); // these are gonna have to be small af
    }

    public double getElevatorKI() {
        return SmartDashboard.getNumber("kI_VLIFT", kI_ELEVATOR);
    }

    public double getElevatorKD() {
        return SmartDashboard.getNumber("kD_VLIFT", kD_ELEVATOR);
    }

    public double getMastKP() {
        return SmartDashboard.getNumber("kP_HLIFT", kP_MAST);
    }

    public double getMastKI() {
        return SmartDashboard.getNumber("kI_HLIFT", kI_MAST);
    }

    public double getMastKD() {
        return SmartDashboard.getNumber("kD_HLIFT", kD_MAST);
    }

    public double getWristKP() {
        return SmartDashboard.getNumber("kP_INTAKE", kP_INTAKE);
    }

    public double getWristKI() {
        return SmartDashboard.getNumber("kI_INTAKE", kI_INTAKE);
    }

    public double getWristKD() {
        return SmartDashboard.getNumber("kD_INTAKE", kD_INTAKE);
    }

    public double getDriveKP() {
        return SmartDashboard.getNumber("kP_DRIVE", kP_DRIVE);
    }

    public double getDriveKI() {
        return SmartDashboard.getNumber("kI_DRIVE", kI_DRIVE);
    }

    public double getDriveKD() {
        return SmartDashboard.getNumber("kD_DRIVE", kD_DRIVE);
    }

    public double getVisionKP() {
        return SmartDashboard.getNumber("kP_VISION", kP_VISION);
    }

    public double getVisionKI() {
        return SmartDashboard.getNumber("kI_VISION", kI_VISION);
    }

    public double getVisionKD() {
        return SmartDashboard.getNumber("kD_VISION", kD_VISION);
    }

    public double getVisionRotKP() {
        return SmartDashboard.getNumber("kP_VISION_ROT", kP_VISION_ROT);
    }

    public double getVisionRotKI() {
        return SmartDashboard.getNumber("kI_VISION_ROT", kI_VISION_ROT);
    }

    public double getVisionRotKD() {
        return SmartDashboard.getNumber("kD_VISION_ROT", kD_VISION_ROT);
    }

    public double getElevatorUnlocked() {
        return SmartDashboard.getNumber("UNLOCKED", ELEVATOR_UNLOCKED);
    }

    public double getElevatorLocked() {
        return SmartDashboard.getNumber("LOCKED", ELEVATOR_LOCKED);
    }

    public double getMaxSpeedUpIntake() {
        return SmartDashboard.getNumber("MAX_SPEED_UP_INTAKE", WRIST_MAX_SPEED_UP);
    }

    public double getMaxSpeedDownIntake() {
        return SmartDashboard.getNumber("MAX_SPEED_DOWN_INTAKE", WRIST_MAX_SPEED_DOWN);
    }

    /**
     * Repeatedly called in Robot.java
     */
    public void outputVariables() {
        SmartDashboard.putNumber("Left Front Percent", DriveTrain.getInstance().getLeftFrontPercent());
        SmartDashboard.putNumber("Left Back Percent", DriveTrain.getInstance().getLeftBackPercent());
        SmartDashboard.putNumber("Right Front Percent", DriveTrain.getInstance().getRightFrontPercent());
        SmartDashboard.putNumber("Right Back Percent", DriveTrain.getInstance().getRightBackPercent());

        SmartDashboard.putNumber("Distance", Vision.getInstance().twoTargetDistance());

        SmartDashboard.putNumber("Fused", IMU.getInstance().getFusedHeading());

        SmartDashboard.putNumber("Initial Yaw", IMU.getInstance().getInitialYaw());
        SmartDashboard.putNumber("Yaw", IMU.getInstance().getYaw());

        SmartDashboard.putNumber("Initial Pitch", IMU.getInstance().getInitialPitch());
        SmartDashboard.putNumber("Pitch", IMU.getInstance().getPitch());

        SmartDashboard.putNumber("Initial Roll", IMU.getInstance().getInitialRoll());
        SmartDashboard.putNumber("Roll", IMU.getInstance().getRoll());

        SmartDashboard.putBoolean("Lift Top", ProxSensors.getInstance().getLiftTopLimit());
        SmartDashboard.putBoolean("Lift Center", ProxSensors.getInstance().getLiftCenterLimit());
        SmartDashboard.putBoolean("Lift Bottom", ProxSensors.getInstance().getLiftBottomLimit());

        SmartDashboard.putNumber("Operator Joystick", OI.getInstance().getOperatorJoystickY());

        SmartDashboard.putNumber("Vertical Lift", Elevator.getInstance().getVerticalPosition());

        SmartDashboard.putNumber("Horizontal Lift", Mast.getInstance().getHorizontalPosition());
        SmartDashboard.putBoolean("Front Prox Sensor", ProxSensors.getInstance().getLiftFrontLimit());
        SmartDashboard.putBoolean("Rear Prox Sensor", ProxSensors.getInstance().getLiftRearLimit());
        SmartDashboard.putBoolean("Intake Top Prox Sensor", ProxSensors.getInstance().getIntakeTopLimit());
    }

}