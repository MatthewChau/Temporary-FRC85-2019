/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Addresses {

    // Drivestation
    public static final int CONTROLLER_DRIVER = 0;
    public static final int CONTROLLER_OPERATOR = 1;

    // DriveTrain
    public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 1;
    public static final int DRIVETRAIN_LEFT_BACK_MOTOR = 2;
    public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 3;
    public static final int DRIVETRAIN_RIGHT_BACK_MOTOR = 4;

    // IMU
    // public static final int IMUTalon = 2;

    // LiftProximitySensors DigitalInputs
    public static final int LIFT_TOP_LIMIT = 0;
    public static final int LIFT_BOTTOM_LIMIT = 1;
    public static final int LIFT_LEFT_LIMIT = 2;
    public static final int LIFT_RIGHT_LIMIT = 3;

    // Lift (addresses are placeholders for now)
    public static final int LIFT_LEFT_MOTOR = 5;
    public static final int LIFT_RIGHT_MOTOR = 6;
    public static final int LIFT_CIM_MOTOR = 7;

    // Intake (addresses are placeholders for now)
    public static final int INTAKE_FLIPPER = 9;
    public static final int INTAKE_PUSHER = 10;
    public static final int INTAKE_ROLLER = 11;

    // Intake Limits (addresses are placeholders for now)
    public static final int INTAKE_TOP_LIMIT = 12;
    public static final int INTAKE_BOTTOM_LIMIT = 13;
    
    //Encoders (addresses are placeholders for now)
    //public static final int LIFT_LEFT_ENCODER = 8;
    //public static final int LIFT_CIM_ENCODER = 9;
    public static final int INTAKE_ENCODER_A = 14;
    public static final int INTAKE_ENCODER_B = 15;

}