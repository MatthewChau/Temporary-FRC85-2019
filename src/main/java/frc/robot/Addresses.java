/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Addresses {

    // Drivestation
    public static final int CONTROLLER_OPERATOR_BLACK = 0;
    public static final int CONTROLLER_OPERATOR_WHITE = 1;
    public static final int CONTROLLER_DRIVER = 2;
    public static final int CONTROLLER_DRIVER_STICK_RIGHT = 2;
    public static final int CONTROLLER_DRIVER_STICK_LEFT = 3;
    public static final int CONTROLLER_OPERATOR_JOYSTICK = 4;

    // controller (gamepad F310)
    // buttons
    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;
    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;
    public static final int BACK_BUTTON = 7;
    public static final int START_BUTTON = 8;
    public static final int LEFT_STICK_IN = 9;
    public static final int RIGHT_STICK_IN = 10;
    // axes
    public static final int LEFT_X_AXIS = 0;
    public static final int LEFT_Y_AXIS = 1;
    public static final int LEFT_TRIGGER = 2; // 0-1 outputs only
    public static final int RIGHT_TRIGGER = 3; // 0-1 outputs only
    public static final int RIGHT_X_AXIS = 4;
    public static final int RIGHT_Y_AXIS = 5;

    // logitech attack 3
    // these buttons have labels that are literally on the controller
    // attack refers to the controller type
    // face refers to everything on the face of the stick
    // base refers to everything on the base of the stick
    public static final int ATTACK_TRIGGER = 1;
    public static final int ATTACK_FACE_BOTTOM = 2;
    public static final int ATTACK_FACE_CENTER = 3;
    public static final int ATTACK_FACE_LEFT = 4;
    public static final int ATTACK_FACE_RIGHT = 5;
    public static final int ATTACK_BASE_LEFT_TOP = 6;
    public static final int ATTACK_BASE_LEFT_BOTTOM = 7;
    public static final int ATTACK_BASE_BOTTOM_LEFT = 8;
    public static final int ATTACK_BASE_BOTTOM_RIGHT = 9;
    public static final int ATTACK_BASE_RIGHT_BOTTOM = 10;
    public static final int ATTACK_BASE_RIGHT_TOP = 11;
    // axes
    public static final int ATTACK_X_AXIS = 0; // right is positive
    public static final int ATTACK_Y_AXIS = 1; // forward is negative
    public static final int ATTACK_DIAL = 2; // down is positive

    // logitech extreme 3d
    // these buttons also have labels that are literally on the controller
    // extreme refers to the controller type
    // face refers to everything on the face of the stick
    // base refers to everything on the base of the stick
    // the base has buttons like so:
    //  __   __
    // | 7| | 8|
    // |__| |__|
    //  __   __
    // | 9| |10| [stick goes]
    // |__| |__| [   here   ]
    //  __   __
    // |11| |12|
    // |__| |__|
    public static final int EXTREME_TRIGGER = 1;
    public static final int EXTREME_THUMB_BUTTON = 2;
    public static final int EXTREME_FACE_BOTTOM_LEFT = 3;
    public static final int EXTREME_FACE_BOTTOM_RIGHT = 4;
    public static final int EXTREME_FACE_TOP_LEFT = 5;
    public static final int EXTREME_FACE_TOP_RIGHT = 6;
    public static final int EXTREME_BASE_SEVEN = 7;
    public static final int EXTREME_BASE_EIGHT = 8;
    public static final int EXTREME_BASE_NINE = 9;
    public static final int EXTREME_BASE_TEN = 10;
    public static final int EXTREME_BASE_ELEVEN = 11;
    public static final int EXTREME_BASE_TWELVE = 12;
    // axes
    public static final int EXTREME_X_AXIS = 0; // right is positive
    public static final int EXTREME_Y_AXIS = 1; // forward is negative
    public static final int EXTREME_ROT_AXIS = 2; // clockwise is positive
    public static final int EXTREME_DIAL = 3; // down is positive


    // Black op station
    public static final int OPERATOR_BLACK_ONE = 1;
    public static final int OPERATOR_BLACK_TWO = 2;

    public static final int OPERATOR_BLACK_THREE = 3;
    public static final int OPERATOR_BLACK_FOUR = 4;
    public static final int OPERATOR_BLACK_FIVE = 5;

    public static final int OPERATOR_BLACK_SIX = 6;
    public static final int OPERATOR_BLACK_SEVEN = 7;
    public static final int OPERATOR_BLACK_EIGHT = 8;

    public static final int OPERATOR_CLIMB_AUTO = 10; // black button on the white joystick

    // White operator station
    public static final int OPERATOR_WHITE_ONE = 1; // old elevator
    public static final int OPERATOR_WHITE_TWO = 2; // old roller
    public static final int OPERATOR_WHITE_THREE = 3;
    public static final int OPERATOR_WHITE_FOUR = 4;
    public static final int OPERATOR_WHITE_FIVE = 5;

    public static final int OPERATOR_WHITE_SIX = 6;
    public static final int OPERATOR_WHITE_SEVEN = 7;
    public static final int OPERATOR_WHITE_EIGHT = 8;

    public static final int OPERATOR_CLIMB_REAR = 9;
    public static final int OPERATOR_CLIMB_FRONT = 9; // white button on the black joystick


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

    // new climb shtuff
    public static final int CLIMB_RIGHT_LIMIT = 6;
    public static final int CLIMB_LEFT_LIMIT = 7;
    public static final int CLIMB_REAR_LIMIT = 8;

    public static final int CLIMB_SERVO = 1;

    public static final int CLIMB_REAR_MOTOR = 51;
    public static final int CLIMB_REAR_DRIVE = 52;
    public static final int CLIMB_FRONT_MOTOR_LEFT = 53;
    public static final int CLIMB_FRONT_MOTOR_RIGHT = 54;

}