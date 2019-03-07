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

    public final double DEADBAND_DRIVERSTICK = 0.1;
    public final double DEADBAND_Z_DRIVERSTICK = 0.3;  
    public final double DEADBAND_OPERATORSTICK = 0.05;
    public final double TOLERANCE_ANGLE = 5.0;
    public final int MAX_TURNS = 7; // if we go over 2520 degrees in either direction in one match then help.

    // PID

    private final double kP_VLIFT = 0.0004, kI_VLIFT = 0.00000004, kD_VLIFT = 0.0009;

    private final double kP_HLIFT = 0.025, kI_HLIFT = 0.0, kD_HLIFT = 0.0;

    public static final double kP_INTAKE = 0.001, kI_INTAKE = 0.0, kD_INTAKE = 0.0;

    // ANGLES FOR VISION

    public final double ROT_POS_1 = 90;
    public final double ROT_POS_2 = -90;
    public final double ROT_POS_3 = 60;
    public final double ROT_POS_4 = 120;

    // ELEVATOR

    // elevator positions
    public final int HATCH_ONE = 1565;
    public final int HATCH_TWO = 8850;
    public final int HATCH_THREE = 17097;
    public final int HATCH_FLOOR = 100;
    
    public final int CARGO_ONE = 5300;
    public final int CARGO_TWO = 13540;
    public final int CARGO_THREE = 21612;
    public final int CARGO_FLOOR = 1500;

    //Elevator position limits
    public static final int ELEVATOR_MAX_POS = 21500;
    public static final int ELEVATOR_MIN_POS_MAST_PROTECTED = 4000;
    public static final int ELEVATOR_MIN_POS_MAST_FORWARD_CARGO = 1100;
    public static final int ELEVATOR_MIN_POS_MAST_FORWARD_HATCH = 0;

    //Elevator Speed Limits 
    public static final double ELEVATOR_MAX_SPEED = .5;
    public static final double ELEVATOR_MIN_SPEED = -.2;

    //Servo Angles for LiftLock
    public final double ELEVATOR_LOCKED = 70;
    public final double ELEVATOR_UNLOCKED = 180;

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
    public static final int Wrist_Floor_Pickup_Pos = -1000000;
    public final int INTAKE_0 = 0;
    public final int INTAKE_45 = -500000;
    public final int INTAKE_90 = -908000;

    //Wrist position limits
    public static final int WRIST_MAX_POS = 0;
    public static final int WRIST_MIN_POS_MAST_BACK = -200000;
    public static final int WRIST_MIN_POS = -1124000;

    // wrist speed limits
    public final double WRIST_MAX_SPEED_UP = 0.8;
    public final double WRIST_MAX_SPEED_DOWN = -0.8;

    /**
     * Put variables here that should be changeable on the fly.
     */
    private Variables() {
        SmartDashboard.putNumber("kP_VLIFT", kP_VLIFT);
        SmartDashboard.putNumber("kI_VLIFT", kI_VLIFT);
        SmartDashboard.putNumber("kD_VLIFT", kD_VLIFT);

        SmartDashboard.putNumber("kP_HLIFT", kP_HLIFT);
        SmartDashboard.putNumber("kI_HLIFT", kI_HLIFT);
        SmartDashboard.putNumber("kD_HLIFT", kD_HLIFT);
        
        SmartDashboard.putNumber("kP_INTAKE", kP_INTAKE);
        SmartDashboard.putNumber("kI_INTAKE", kI_INTAKE);
        SmartDashboard.putNumber("kD_INTAKE", kD_INTAKE);

        SmartDashboard.putNumber("kP_DRIVE", 0.05);
        SmartDashboard.putNumber("kI_DRIVE", 0.0);
        SmartDashboard.putNumber("kD_DRIVE", 0.0);

        SmartDashboard.putNumber("kP_VISION", 0.05);
        SmartDashboard.putNumber("kI_VISION", 0.0);
        SmartDashboard.putNumber("kD_VISION", 0.0);

        SmartDashboard.putNumber("kP_VISION_ROT", 0.012);
        SmartDashboard.putNumber("kI_VISION_ROT", 0.0);
        SmartDashboard.putNumber("kD_VISION_ROT", 0.2);

        SmartDashboard.putNumber("MAX_SPEED_UP_INTAKE", WRIST_MAX_SPEED_UP);
        SmartDashboard.putNumber("MAX_SPEED_DOWN_INTAKE", WRIST_MAX_SPEED_DOWN);

        SmartDashboard.putNumber("UNLOCKED", ELEVATOR_UNLOCKED);
        SmartDashboard.putNumber("LOCKED", ELEVATOR_LOCKED);

        SmartDashboard.putBoolean("Joysticks Enabled", true);
        SmartDashboard.putBoolean("Disable Intake Top Limit", true);

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
        return SmartDashboard.getNumber("kP_VLIFT", kP_VLIFT); // these are gonna have to be small af
    }

    public double getElevatorKI() {
        return SmartDashboard.getNumber("kI_VLIFT", kI_VLIFT);
    }

    public double getElevatorKD() {
        return SmartDashboard.getNumber("kD_VLIFT", kD_VLIFT);
    }

    public double getMastKP() {
        return SmartDashboard.getNumber("kP_HLIFT", kP_HLIFT);
    }

    public double getMastKI() {
        return SmartDashboard.getNumber("kI_HLIFT", kI_HLIFT);
    }

    public double getMastKD() {
        return SmartDashboard.getNumber("kD_HLIFT", kD_HLIFT);
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
        return SmartDashboard.getNumber("kP_DRIVE", 0.05);
    }

    public double getDriveKI() {
        return SmartDashboard.getNumber("kI_DRIVE", 0.0);
    }

    public double getDriveKD() {
        return SmartDashboard.getNumber("kD_DRIVE", 0.0);
    }

    public double getVisionKP() {
        return SmartDashboard.getNumber("kP_VISION", 0.05);
    }

    public double getVisionKI() {
        return SmartDashboard.getNumber("kI_VISION", 0.0);
    }

    public double getVisionKD() {
        return SmartDashboard.getNumber("kD_VISION", 0.0);
    }

    public double getVisionRotKP() {
        return SmartDashboard.getNumber("kP_VISION_ROT", 0.012);
    }

    public double getVisionRotKI() {
        return SmartDashboard.getNumber("kI_VISION_ROT", 0.0);
    }

    public double getVisionRotKD() {
        return SmartDashboard.getNumber("kD_VISION_ROT", 0.2);
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
        SmartDashboard.putBoolean("Lift Front", ProxSensors.getInstance().getLiftFrontLimit());

        SmartDashboard.putNumber("Operator Joystick", OI.getInstance().getOperatorJoystickY());

        SmartDashboard.putNumber("Vertical Lift", Elevator.getInstance().getVerticalPosition());

        SmartDashboard.putNumber("Horizontal Lift", Mast.getInstance().getHorizontalPosition());
        SmartDashboard.putBoolean("Front Prox Sensor", ProxSensors.getInstance().getLiftFrontLimit());
        SmartDashboard.putBoolean("Rear Prox Sensor", ProxSensors.getInstance().getLiftRearLimit());

        SmartDashboard.putNumber("Intake Encoder", Intake.getInstance().getWristPosition());

        //SmartDashboard.putBoolean("Prox me OwO", ProxSensors.getInstance().getTopLimit());
    }

}