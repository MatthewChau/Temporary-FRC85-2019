/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Addresses {

    // Drivestation
    public static final int CONTROLLER_DRIVER = 2;
    public static final int CONTROLLER_OPERATOR_BLACK = 0;
    public static final int CONTROLLER_OPERATOR_WHITE = 1;
    public static final int CONTROLLER_DRIVER_STICK_RIGHT = 2;
    public static final int CONTROLLER_DRIVER_STICK_LEFT = 3;

    // Black op station
    public static final int OPERATOR_LIFT_HORIZONTAL = 1;
    public static final int OPERATOR_INTAKE_ROTATE = 2;

    public static final int OPERATOR_HATCH_STATION = 3;
    public static final int OPERATOR_HATCH_RELEASE = 4;
    public static final int OPERATOR_HATCH_FLOOR = 5;

    public static final int OPERATOR_HATCH_THREE = 6;
    public static final int OPERATOR_HATCH_TWO = 7;
    public static final int OPERATOR_HATCH_ONE = 8;

    public static final int OPERATOR_CLIMB_AUTO = 10; // on the white joystick

    // White operator station
    public static final int OPERATOR_LIFT_VERTICAL = 1;

    public static final int OPERATOR_CARGO_IN = 2;
    public static final int OPERATOR_CARGO_DEFAULT = 3;
    public static final int OPERATOR_CARGO_OUT = 4;
    public static final int OPERATOR_CARGO_FLOOR = 5;

    public static final int OPERATOR_CARGO_THREE = 6;
    public static final int OPERATOR_CARGO_TWO = 7;
    public static final int OPERATOR_CARGO_ONE = 8;

    public static final int OPERATOR_CLIMB_BACK = 9;
    public static final int OPERATOR_CLIMB_FRONT = 9; // on the black joystick

    // DriveTrain
    public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 11;
    public static final int DRIVETRAIN_LEFT_BACK_MOTOR = 12;
    public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 13;
    public static final int DRIVETRAIN_RIGHT_BACK_MOTOR = 14;

    // Lift
    public static final int LIFT_LEFT_MOTOR = 21;
    public static final int LIFT_RIGHT_MOTOR = 22;
    public static final int LIFT_CIM_MOTOR = 23;

    public static final int LIFT_SERVO = 0;

    // Lift Proximity Sensors (DigitalInputOutputs)
    public static final int LIFT_TOP_LIMIT = 0;
    public static final int LIFT_CENTER_LIMIT = 1;
    public static final int LIFT_BOTTOM_LIMIT = 2;
    public static final int LIFT_FRONT_LIMIT = 3;
    public static final int LIFT_BACK_LIMIT = 4;

    // Intake 
    public static final int INTAKE_WRIST = 31;
    public static final int INTAKE_ROLLER = 32;

    // Intake Proximity Sensors
    public static final int INTAKE_TOP_LIMIT = 5;

    // BeltTrain
    public static final int BELTTRAIN_LEFT_MOTOR = 41;
    public static final int BELTTRAIN_RIGHT_MOTOR = 42;

    // Belt Solenoid
    public static final int BELTTRAIN_SOLENOID = 0;

    // Rear Solenoid
    public static final int REAR_SOLENOID_1 = 1;
    public static final int REAR_SOLENOID_2 = 2;
   
    // LED Spike Relay
    public static final int LIGHT_RELAY = 0;

    // Climb Motors
    public static final int REAR_CLIMB_MOTOR = 51;
    public static final int REAR_CLIMB_DRIVE = 52;
    public static final int FRONT_CLIMB_MOTOR_ONE = 53;
    public static final int FRONT_CLIMB_MOTOR_TWO = 54;
    

}