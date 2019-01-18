/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Addresses {

    // Drivestation
    public static final int LEFT_JOYSTICK = 0;
    public static final int RIGHT_JOYSTICK = 1;

    // DriveTrain
    public static final int DRIVETRAIN_LEFT_FRONT_MOTOR = 1;
    public static final int DRIVETRAIN_LEFT_BACK_MOTOR = 2;
    public static final int DRIVETRAIN_RIGHT_FRONT_MOTOR = 3;
    public static final int DRIVETRAIN_RIGHT_BACK_MOTOR = 4;

    //Lift (addresses are placeholders for now)
    public static final int LIFT_LEFT_MOTOR = 5;
    public static final int LIFT_RIGHT_MOTOR = 6;
    public static final int CIM_MOTOR = 7;
    public static final int LIFT_PHASE_0 = 0; //The encoder value for the position at phase 0
    public static final int LIFT_PHASE_1 = 3; //The encoder value for the position at phase 1
    public static final int LIFT_PHASE_2 = 6; //The encoder value for the position at phase 2
    public static final int LIFT_PHASE_3 = 9; //The encoder value for the position at phase 3
}