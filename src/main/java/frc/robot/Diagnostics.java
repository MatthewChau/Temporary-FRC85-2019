/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.sensors.IMU;
import frc.robot.sensors.RangeFinder;
import frc.robot.sensors.Sensors;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Diagnostics {

    File log;
    BufferedWriter out = null;

    private int _placeHolder = 0;

    public void init() {
        try {
            close();

            _placeHolder = 0;

            String date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss")
                    .format(new java.util.Date(System.currentTimeMillis()));
            log = new File("home/lvuser/log " + date + ".csv");
            log.createNewFile();
            out = new BufferedWriter(new FileWriter(log, true));
            out.append("Time,Match Time,"
                    + "Driver Left Joystick X Input,Driver Left Joystick Y Input,"
                    + "Left Front Motor Voltage,Left Front Motor Current,Left Back Motor Voltage,Left Back Motor Current,Right Front Motor Voltage,Right Front Motor Current,Right Back Motor Voltage,Right Back Motor Current,"
                    + "Left Front Pos,Left Back Pos,Right Front Pos,Right Back Pos,"
                    + "Lift Top Limit,Lift Bottom Limit,Lift Front Limit,Lift Rear Limit,"
                    + "Elevator Position,Mast Position,"
                    + "Elevator Left Motor Voltage,Elevator Left Motor Current,Elevator Right Motor Voltage,Elevator Right Motor Current,Mast Motor Voltage,Mast Motor Current,"
                    + "Intake Top Limit,Wrist Position,"
                    + "Wrist Motor Voltage,Wrist Motor Current,Roller Motor Voltage,Roller Motor Current,"
                    + "Yaw,Pitch,Roll,"
                    + "Range Finder Distance,"
                    + "Black1,Black2,Hatch Station,Hatch Release,Hatch Ground,Hatch 3,Hatch 2,Hatch 1,Climb Front,Auto Climb,"
                    + "White1,Roller In,Cargo Ship,Roller Out,Cargo Ground,Cargo 3, Cargo 2, Cargo 1,Climb Rear,"
                    + "DriverLeftTrigger,DriverLeftFaceBottom,DriverLeftFaceCenter,DriverLeftFaceLeft,DriverLeftFaceRight,DriverLeftBaseLeftTop,DriverLeftBaseLeftBottom,DriverLeftBaseBottomLeft,DriverLeftBaseBottomRight,DriverLeftBaseRightBottom,DriverLeftBaseRightTop"
                    + "DriverRightTrigger,DriverRightThumbButton,DriverRightFaceBottomLeft,DriverRightFaceBottomRight,DriverRightFaceTopLeft,DriverRightFaceTopRight,DriverRight7,DriverRight8,DriverRight9,DriverRight10,DriverRight11,DriverRight12"
                    + "OpJoystickTrigger,OpJoystickThumbButton,OpJoystickFaceBottomLeft,OpJoystickFaceBottomRight,OpJoystickFaceTopLeft,OpJoystickFaceTopRight,OpJoystick,OpJoystick7,OpJoystick8,OpJoystick9,OpJoystick10,OpJoystick11,OpJoystick12"
                    + ",");
            SmartDashboard.putString("Diagnostics Error Creating File", "OK");
            out.newLine();
        } catch (Exception ex) {
            System.out.println("Error creating log file: " + ex.toString());
            SmartDashboard.putString("Diagnostics Error Creating File", ex.toString());
        }
    }

    /**
     * runs repeatedly to get values and such
     */
    public void log() {
        try{
            if (out == null) {
                init();
            }
            _placeHolder++;
            String time = Integer.toString(_placeHolder);
            String matchTime = Double.toString(DriverStation.getInstance().getMatchTime());

            String leftJoystickXInput = Double.toString(OI.getInstance().getLeftXInputJoystick());
            String leftJoystickYInput = Double.toString(OI.getInstance().getLeftYInputJoystick());

            String leftFrontMotor = Double.toString(DriveTrain.getInstance().getLeftFrontVoltage());
                String leftFrontCurrent = Double.toString(DriveTrain.getInstance().getLeftFrontCurrent());
            String leftBackMotor = Double.toString(DriveTrain.getInstance().getLeftBackVoltage());
                String leftBackCurrent = Double.toString(DriveTrain.getInstance().getLeftBackCurrent());
            String rightFrontMotor = Double.toString(DriveTrain.getInstance().getRightFrontVoltage());
                String rightFrontCurrent = Double.toString(DriveTrain.getInstance().getRightFrontCurrent());
            String rightBackMotor = Double.toString(DriveTrain.getInstance().getRightBackVoltage());
                String rightBackCurrent = Double.toString(DriveTrain.getInstance().getRightBackCurrent());

            String leftFrontPos = Integer.toString(DriveTrain.getInstance().getLeftFrontPosition());
            String leftBackPos = Integer.toString(DriveTrain.getInstance().getLeftBackPosition());
            String rightFrontPos = Integer.toString(DriveTrain.getInstance().getRightFrontPosition());
            String rightBackPos = Integer.toString(DriveTrain.getInstance().getRightBackPosition());

            String liftTopLimit = Boolean.toString(Sensors.getInstance().getLiftTopLimit());
            String liftBottomLimit = Boolean.toString(Sensors.getInstance().getLiftBottomLimit());
            String liftFrontLimit = Boolean.toString(Sensors.getInstance().getLiftFrontLimit());
            String liftRearLimit = Boolean.toString(Sensors.getInstance().getLiftRearLimit());

            String elevatorPos = Integer.toString(Elevator.getInstance().getElevatorPosition());
            String mastPos = Integer.toString(Mast.getInstance().getMastPosition());

            String elevatorLeftMotor = Double.toString(Elevator.getInstance().getElevatorLeftVoltage());
                String elevatorLeftCurrent = Double.toString(Elevator.getInstance().getElevatorLeftCurrent());
            String elevatorRightMotor = Double.toString(Elevator.getInstance().getElevatorRightVoltage());
                String elevatorRightCurrent = Double.toString(Elevator.getInstance().getElevatorRightCurrent());
            String mastMotor = Double.toString(Mast.getInstance().getMastMotorVoltage());
                String mastMotorCurrent = Double.toString(Mast.getInstance().getMastMotorCurrent());

            String intakeTopLimit = Boolean.toString(Sensors.getInstance().getIntakeTopLimit());
            String wristPos = Integer.toString(Intake.getInstance().getWristPosition());
            
            String wristMotor = Double.toString(Intake.getInstance().getWristMotorVoltage());
                String wristCurrent = Double.toString(Intake.getInstance().getWristMotorCurrent());
            String rollerMotor = Double.toString(Intake.getInstance().getRollerMotorVoltage());
                String rollerCurrent = Double.toString(Intake.getInstance().getRollerMotorCurrent());

            String yaw = Double.toString(IMU.getInstance().getYaw());
            String pitch = Double.toString(IMU.getInstance().getPitch());
            String roll = Double.toString(IMU.getInstance().getRoll());

            String rangeFinderDistance = Double.toString(RangeFinder.getInstance().getDistance());

            int i;
            int j;
            String[][] bools = new String[Addresses.NUM_CONTROLLERS + 1][Addresses.EXTREME_NUM_BUTTONS]; // maximum amount of numbers
            for (i = 0; i <= Addresses.NUM_CONTROLLERS; i++) {
                for (j = 1; j < Addresses.EXTREME_NUM_BUTTONS; j++) {
                    bools[i][j] = Boolean.toString(OI.getInstance().getGenericButton(i, j));
                }
            }

            out.append(time + "," + matchTime + ","
                    + leftJoystickXInput + "," + leftJoystickYInput + ","
                    + leftFrontMotor + "," + leftFrontCurrent + "," + leftBackMotor + "," + leftBackCurrent + "," + rightFrontMotor + "," + rightFrontCurrent + "," + rightBackMotor + "," + rightBackCurrent + ","
                    + leftFrontPos + "," + leftBackPos + "," + rightFrontPos + "," + rightBackPos + ","
                    + liftTopLimit + "," + liftBottomLimit + "," + liftFrontLimit + "," + liftRearLimit + ","
                    + elevatorPos + "," + mastPos + ","
                    + elevatorLeftMotor + "," + elevatorLeftCurrent + "," + elevatorRightMotor + "," + elevatorRightCurrent + "," + mastMotor + "," + mastMotorCurrent + ","
                    + intakeTopLimit + "," + wristPos + ","
                    + wristMotor + "," + wristCurrent + "," + rollerMotor + "," + rollerCurrent + ","
                    + yaw + "," + pitch + "," + roll + ","
                    + rangeFinderDistance + ","
                    + bools[0][0] + "," + bools[0][1] + "," + bools[0][2] + "," + bools[0][3] + "," + bools[0][4] + "," + bools[0][5] + "," + bools[0][6] + "," + bools[0][7] + "," + bools[0][8] + "," + bools[0][9] + ","
                    + bools[1][0] + "," + bools[1][1] + "," + bools[1][2] + "," + bools[1][3] + "," + bools[1][4] + "," + bools[1][5] + "," + bools[1][6] + "," + bools[1][7] + "," + bools[1][8] + ","
                    + bools[2][0] + "," + bools[2][1] + "," + bools[2][2] + "," + bools[2][3] + "," + bools[2][4] + "," + bools[2][5] + "," + bools[2][6] + "," + bools[2][7] + "," + bools[2][8] + "," + bools[2][9] + "," + bools[2][10] + ","
                    + bools[3][0] + "," + bools[3][1] + "," + bools[3][2] + "," + bools[3][3] + "," + bools[3][4] + "," + bools[3][5] + "," + bools[3][6] + "," + bools[3][7] + "," + bools[3][8] + "," + bools[3][9] + "," + bools[3][10] + "," + bools[3][11] + ","
                    + bools[4][0] + "," + bools[4][1] + "," + bools[4][2] + "," + bools[4][3] + "," + bools[4][4] + "," + bools[4][5] + "," + bools[4][6] + "," + bools[4][7] + "," + bools[4][8] + "," + bools[4][9] + "," + bools[4][10] + "," + bools[4][11] + ",");
            SmartDashboard.putString("Diagnostics Error Writing", "OK");
            out.newLine();
        } catch (Exception ex) {
            System.out.println("Error writing diagnostic log: " + ex.toString());
            SmartDashboard.putString("Diagnostics Error Writing", ex.toString());
        }
    }

    public void close() {
        if (out != null) {
            try {
                out.close();
                out = null;
                SmartDashboard.putString("Diagnostics Error Closing", "OK");
            } catch (Exception ex) {
                System.out.println("Error closing file: " + ex.toString());
                SmartDashboard.putString("Diagnostics Error Closing", ex.toString());
            }
        }
    }
}