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
    public static final int CONTROLLER_OPERATOR_JOYSTICK = 4;
    public static final int CONTROLLER_DRIVER_STICK_RIGHT = 2;
    public static final int CONTROLLER_DRIVER_STICK_LEFT = 3;
  

    // Black op station
    public static final int OPERATOR_BLACK_ONE = 1; // old mast
    public static final int OPERATOR_BLACK_TWO = 2; // old wrist

    public static final int OPERATOR_HATCH_STATION = 3;
    public static final int OPERATOR_HATCH_RELEASE = 4;
    public static final int OPERATOR_HATCH_FLOOR = 5;

    public static final int OPERATOR_HATCH_THREE = 6;
    public static final int OPERATOR_HATCH_TWO = 7;
    public static final int OPERATOR_HATCH_ONE = 8;

    public static final int OPERATOR_CLIMB_AUTO = 10; // on the white joystick

    // White operator station
    public static final int OPERATOR_WHITE_ONE = 1; // old elevator
    public static final int OPERATOR_WHITE_TWO = 2; // old roller
    public static final int OPERATOR_CARGO_DEFAULT = 3;
    public static final int OPERATOR_CARGO_OUT = 4;
    public static final int OPERATOR_CARGO_FLOOR = 5;

    public static final int OPERATOR_CARGO_THREE = 6;
    public static final int OPERATOR_CARGO_TWO = 7;
    public static final int OPERATOR_CARGO_ONE = 8;

    public static final int OPERATOR_CLIMB_REAR = 9;
    public static final int OPERATOR_CLIMB_FRONT = 9; // on the black joystick

    // New Op Stick
    public static final int OPERATOR_INTAKE_ROTATE = 2;
    public static final int OPERATOR_LIFT_VERTICAL = 3;
    public static final int OPERATOR_LIFT_HORIZONTAL = 4;
    public static final int OPERATOR_CARGO_IN = 5;

    // DriveTrain
    public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 11;
    public static final int DRIVETRAIN_LEFT_BACK_MOTOR = 12;
    public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 13;
    public static final int DRIVETRAIN_RIGHT_BACK_MOTOR = 14;

    // Elevator
    public static final int LIFT_LEFT_MOTOR = 21;
    public static final int LIFT_RIGHT_MOTOR = 22;
    public static final int LIFT_CIM_MOTOR = 23;

    public static final int LIFT_SERVO = 0;

    // Elevator Proximity Sensors (DigitalInputOutputs)
    public static final int LIFT_TOP_LIMIT = 0;
    public static final int LIFT_CENTER_LIMIT = 1;
    public static final int LIFT_BOTTOM_LIMIT = 2;
    public static final int LIFT_FRONT_LIMIT = 3;
    public static final int LIFT_BACK_LIMIT = 4;

    // Intake 
    public static final int INTAKE_WRIST = 31;
    public static final int INTAKE_ROLLER = 32;

    // Wrist Proximity Sensors
    public static final int INTAKE_TOP_LIMIT = 5;
   
    // LED Spike Relay
    public static final int LIGHT_RELAY = 0;

    // Climb Motors
    public static final int CLIMB_REAR_MOTOR = 51;
    public static final int CLIMB_REAR_DRIVE = 52;
    public static final int CLIMB_FRONT_MOTOR_LEFT = 53;
    public static final int CLIMB_FRONT_MOTOR_RIGHT = 54;

}