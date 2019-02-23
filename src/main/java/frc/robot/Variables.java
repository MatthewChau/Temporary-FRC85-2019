/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LiftHorizontal;
import frc.robot.subsystems.LiftVertical;
import frc.robot.sensors.ProxSensors;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Variables {

    private static Variables _instance;
    
    // DRIVETRAIN

    public final double DEADBAND = 0.05;
    public final double DEADBAND_LIFT = 0.05;
    public final double TOLERANCE_ANGLE = 6.0;
    public final int MAX_TURNS = 7; // if we go over 2520 degrees in either direction in one match then help.

    // LIFT

    // Lift Vertical PID
    private final double kP_VLIFT = 0.01, kI_VLIFT = 0.0, kD_VLIFT = 0.0;

    // Lift Horizontal PID
    private final double kP_HLIFT = 0.1, kI_HLIFT = 0.000001, kD_HLIFT = 0.2;

    // Lift Vertical Postitions
    public final int HATCH_LOW = 1000;
    public final int HATCH_MIDDLE = 10000;
    public final int HATCH_HIGH = 20002;
    public final int HATCH_LOADING_STATION = 15;
    public final int HATCH_FLOOR = 5;
    public final int HATCH_DROP = 5;
    
    public final int CARGO_LOW = 20;
    public final int CARGO_MIDDLE = 120;
    public final int CARGO_HIGH = 1200;
    public final int CARGO_LOADING_STATION = 95;
    public final int CARGO_FLOOR = 5;

    public final double ROT_POS_1 = 90;
    public final double ROT_POS_2 = -90;
    public final double ROT_POS_3 = 60;
    public final double ROT_POS_4 = 120;

    // Lift Horizontal Positions
    public static int platform = 0;
    public static int bumpers = 0;
    public static int CENTER_DRIVE = 0;
    public static int CLIMB_HAB = 0;

    public static final int HORIZONTAL_PROTECTED = 7;
    public static final int HORIZONTAL_UNPROTECTED = 0;
    public static final int BUMPERS_TWO = 3;
    public static final int range = 0;

    // INTAKE

    public final double MAX_SPEED_UP_INTAKE = 0.3;
    public final double MAX_SPEED_DOWN_INTAKE = -0.1;

    // Intake PID

    public static final double kP_INTAKE = 0.1, kI_INTAKE = 0.000001, kD_INTAKE = 0.2;

    // Intake Positions and Degrees
    public static final int INTAKE_DEGREE_ONE = 0;
    public static final int INTAKE_DEGREE_TWO = 90;
    public static final int INTAKE_DEGREE_THREE = 95;


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

        SmartDashboard.putNumber("MAX_SPEED_UP_INTAKE", MAX_SPEED_UP_INTAKE);
        SmartDashboard.putNumber("MAX_SPEED_DOWN_INTAKE", MAX_SPEED_DOWN_INTAKE);

        SmartDashboard.putBoolean("Joysticks Enabled", false);
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
    public double getVerticalLiftKP() {
        return SmartDashboard.getNumber("kP_VLIFT", kP_VLIFT); // these are gonna have to be small af
    }

    public double getVerticalLiftKI() {
        return SmartDashboard.getNumber("kI_VLIFT", kI_VLIFT);
    }

    public double getVerticalLiftKD() {
        return SmartDashboard.getNumber("kD_VLIFT", kD_VLIFT);
    }

    public double getHorizontalLiftKP() {
        return SmartDashboard.getNumber("kP_HLIFT", kP_HLIFT);
    }

    public double getHorizontalLiftKI() {
        return SmartDashboard.getNumber("kI_HLIFT", kI_HLIFT);
    }

    public double getHorizontalLiftKD() {
        return SmartDashboard.getNumber("kD_HLIFT", kD_HLIFT);
    }

    public double getIntakeKP() {
        return SmartDashboard.getNumber("kP_INTAKE", kP_INTAKE);
    }

    public double getIntakeKI() {
        return SmartDashboard.getNumber("kI_INTAKE", kI_INTAKE);
    }

    public double getIntakeKD() {
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

    public double getMaxSpeedUpIntake() {
        return SmartDashboard.getNumber("MAX_SPEED_UP_INTAKE", MAX_SPEED_UP_INTAKE);
    }

    public double getMaxSpeedDownIntake() {
        return SmartDashboard.getNumber("MAX_SPEED_DOWN_INTAKE", MAX_SPEED_DOWN_INTAKE);
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

        SmartDashboard.putNumber("Operator Joystick", OI.getInstance().getOperatorJoystick());

        SmartDashboard.putNumber("Vertical Lift", LiftVertical.getInstance().getVerticalPosition());

        SmartDashboard.putNumber("Horizontal Lift", LiftHorizontal.getInstance().getHorizontalPosition());
        SmartDashboard.putBoolean("Front Prox Sensor", ProxSensors.getInstance().getLiftFrontLimit());
        SmartDashboard.putBoolean("Rear Prox Sensor", ProxSensors.getInstance().getLiftRearLimit());

        SmartDashboard.putNumber("Intake Encoder", Intake.getInstance().getFlipperPosition());

        //SmartDashboard.putBoolean("Prox me OwO", ProxSensors.getInstance().getTopLimit());
    }

}