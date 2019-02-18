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
    public static final int CONTROLLER_OPERATOR1 = 4; // will change these later
    public static final int CONTROLLER_OPERATOR2 = 3; // will change these later
    public static final int CONTROLLER_DRIVER_STICK_RIGHT = 0;
    public static final int CONTROLLER_DRIVER_STICK_LEFT = 1;

    // DriveTrain
    public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 11;
    public static final int DRIVETRAIN_LEFT_BACK_MOTOR = 12;
    public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 13;
    public static final int DRIVETRAIN_RIGHT_BACK_MOTOR = 14;

    // Lift
    public static final int LIFT_LEFT_MOTOR = 21;
    public static final int LIFT_RIGHT_MOTOR = 22;
    public static final int LIFT_CIM_MOTOR = 23;

    // Lift Proximity Sensors (DigitalInputOutputs)
    public static final int LIFT_TOP_LIMIT = 0;
    public static final int LIFT_CENTER_LIMIT = 1;
    public static final int LIFT_BOTTOM_LIMIT = 2;
    public static final int LIFT_FRONT_LIMIT = 3;
    public static final int LIFT_BACK_LIMIT = 4;

    // Intake 
    public static final int INTAKE_FLIPPER = 31;
    public static final int INTAKE_PUSHER = 32;
    public static final int INTAKE_ROLLER = 33;

    // Intake Proximity Sensors
    public static final int INTAKE_TOP_LIMIT = 5;
    public static final int INTAKE_BOTTOM_LIMIT = 6;

    // BeltTrain
    public static final int BELTTRAIN_LEFT_MOTOR = 41;
    public static final int BELTTRAIN_RIGHT_MOTOR = 42;

    // Belt Solenoid
    public static final int BELTTRAIN_SOLENOID = 0;

    // Rear Solenoid
    public static final int REAR_SOLENOID = 1;
   
}