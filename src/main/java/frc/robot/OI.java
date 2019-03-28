/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.Variables;
import frc.robot.subsystems.ClimbFront;
import frc.robot.subsystems.ClimbRear;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Intake;

import frc.robot.sensors.IMU;

import frc.robot.commands.drivetrain.FollowOneTarget;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.ActivateIntake;
import frc.robot.commands.intake.WristWithJoystick;
import frc.robot.commands.lift.MastWithJoystick;
import frc.robot.commands.lift.ElevatorWithJoystick;
import frc.robot.commands.climb.ClimbFrontWithJoystick;
import frc.robot.commands.climb.ClimbRearWithJoystick;
import frc.robot.commands.climb.MoveClimbPosition;
import frc.robot.commands.driverassistance.Place;
import frc.robot.commands.driverassistance.CargoStationOne;
import frc.robot.commands.driverassistance.CargoStationTwo;
import frc.robot.commands.driverassistance.CargoGroundOne;
import frc.robot.commands.driverassistance.CargoGroundTwo;
import frc.robot.commands.driverassistance.HatchStationOne;
import frc.robot.commands.driverassistance.HatchStationTwo;
import frc.robot.commands.driverassistance.HatchGroundOne;
import frc.robot.commands.driverassistance.HatchGroundTwo;
import frc.robot.commands.driverassistance.HatchRelease;
import frc.robot.commands.driverassistance.Interrupt;
import frc.robot.commands.driverassistance.ZeroSystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import java.util.Arrays;

public class OI {

    private static OI _instance;

    private Joystick _driverJoystickRight, _driverJoystickLeft;
    private Joystick _operatorControllerWhite, _operatorControllerBlack, _operatorJoystick;

    private JoystickButton _rightJoystickTrigger, _rightJoystickThumbButton, _rightJoystickFaceBottomLeft,
            _rightJoystickFaceBottomRight, _rightJoystickFaceTopLeft, _rightJoystickFaceTopRight, _rightJoystickSeven,
            _rightJoystickEight, _rightJoystickNine, _rightJoystickTen, _rightJoystickEleven, _rightJoystickTwelve;

    private JoystickButton _leftJoystickTrigger, _leftJoystickFaceBottom, _leftJoystickFaceCenter,
            _leftJoystickFaceLeft, _leftJoystickFaceRight, _leftJoystickBaseLeftTop, _leftJoystickBaseLeftBottom,
            _leftJoystickBaseBottomLeft, _leftJoystickBaseBottomRight, _leftJoystickBaseRightBottom,
            _leftJoystickBaseRightTop;

    private JoystickButton _opJoystickTrigger, _opJoystickThumbButton, _opJoystickFaceBottomLeft,
            _opJoystickFaceBottomRight, _opJoystickFaceTopLeft, _opJoystickFaceTopRight, _opJoystickSeven,
            _opJoystickEight, _opJoystickNine, _opJoystickTen, _opJoystickEleven, _opJoystickTwelve;

    // White
    private JoystickButton _operatorWhiteOne, _operatorWhiteTwo, _operatorWhiteThree, _operatorWhiteFour, 
            _operatorWhiteFive, _operatorWhiteSix, _operatorWhiteEight, _operatorWhiteSeven, _operatorClimbFront, _operatorClimbRear;
    // Black
    private JoystickButton _operatorBlackOne, _operatorBlackTwo, _operatorBlackThree, _operatorBlackFour, 
            _operatorBlackFive, _operatorBlackSix, _operatorBlackSeven, _operatorBlackEight, _operatorClimbAuto;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;

    private double _gyroAngle;

    public static final int ROT_SYSTEM = 0;
    public static final int ELEVATOR_SYSTEM = 1;
    public static final int MAST_SYSTEM = 2;
    public static final int VISION_X_SYSTEM = 3;
    public static final int VISION_Y_SYSTEM = 4;
    public static final int VISION_ROT_SYSTEM = 5;
    public static final int CLIMB_SYSTEM = 6;
    public static final int CLIMB_POS_SYSTEM = 7;
    public static final int CLIMB_PITCH_SYSTEM = 8;
    public static final int INTAKE_SYSTEM = 9;

    private int NUM_LOG_ENTRIES = 5;

    public boolean[] firstRun = new boolean[INTAKE_SYSTEM + 1];
    public double[] errorSum = new double[INTAKE_SYSTEM + 1];
    public double[] lastActual = new double[INTAKE_SYSTEM + 1];
    public double[] lastOutput = new double[INTAKE_SYSTEM + 1];
    public double[][] errorLog = new double[INTAKE_SYSTEM + 1][NUM_LOG_ENTRIES];

    public double[] stopArray = new double[4];

    private OI() {
        _operatorControllerBlack = new Joystick(Addresses.CONTROLLER_OPERATOR_BLACK); // op board
        _operatorControllerWhite = new Joystick(Addresses.CONTROLLER_OPERATOR_WHITE); // op board

        _driverJoystickLeft = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_LEFT); // logitech attack
        _driverJoystickRight = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_RIGHT); // logitech extreme 3d

        _operatorJoystick = new Joystick(Addresses.CONTROLLER_OPERATOR_JOYSTICK); // logitech extreme 3d

        // black
        _operatorBlackOne = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_ONE);
        _operatorBlackTwo = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_TWO);
        _operatorBlackThree = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_THREE);
        _operatorBlackThree.whenPressed(new Place(Variables.HATCH_STATION, Variables.WRIST_30, Variables.MAST_FORWARD_POS));
        _operatorBlackThree.whenReleased(new Place(Variables.HATCH_STATION, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
        _operatorBlackFour = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_FOUR);
        _operatorBlackFour.whenPressed(new Place(Variables.ELEVATOR_CURRENT_POS, Variables.WRIST_30, (Variables.MAST_FORWARD_POS - 100000)));
        _operatorBlackFour.whenReleased(new Place(Variables.ELEVATOR_CURRENT_POS, Variables.WRIST_CURR_POSITION, Variables.MAST_PROTECTED));
        _operatorBlackFive = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_FIVE);
        _operatorBlackFive.whenPressed(new HatchGroundOne());
        _operatorBlackFive.whenReleased(new HatchGroundTwo());
        _operatorBlackSix = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_SIX);
        _operatorBlackSix.whenPressed(new Place(Variables.HATCH_THREE, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
        _operatorBlackSeven = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_SEVEN);
        _operatorBlackSeven.whenPressed(new Place(Variables.HATCH_TWO, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
        _operatorBlackEight = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_BLACK_EIGHT);
        _operatorBlackEight.whenPressed(new Place(Variables.HATCH_ONE, Variables.WRIST_0, Variables.MAST_FORWARD_POS));

        // white
        _operatorWhiteOne = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_ONE);
        _operatorWhiteTwo = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_TWO);
        _operatorWhiteTwo.whenPressed(new ActivateIntake(Variables.ROLLER_IN));
        _operatorWhiteTwo.whenReleased(new ActivateIntake(0));
        _operatorWhiteThree = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_THREE);
        _operatorWhiteThree.whenPressed(new Place(Variables.CARGO_SHIP, Variables.WRIST_90, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorWhiteFour = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_FOUR);
        _operatorWhiteFour.whenPressed(new ActivateIntake(Variables.ROLLER_OUT));
        _operatorWhiteFour.whenReleased(new ActivateIntake(0));
        _operatorWhiteFive = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_FIVE);
        _operatorWhiteFive.whenPressed(new CargoGroundOne());
        _operatorWhiteFive.whenReleased(new CargoGroundTwo());
        _operatorWhiteSix = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_SIX);
        _operatorWhiteSix.whenPressed(new Place(Variables.CARGO_THREE, Variables.WRIST_CARGO_HIGH, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorWhiteSeven = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_SEVEN);
        _operatorWhiteSeven.whenPressed(new Place(Variables.CARGO_TWO, Variables.WRIST_CARGO, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorWhiteEight = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_WHITE_EIGHT);
        _operatorWhiteEight.whenPressed(new Place(Variables.CARGO_ONE, Variables.WRIST_CARGO, Variables.MAST_FORWARD_FOR_CARGO));

        // the thing off to the side
        _operatorClimbFront = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_CLIMB_FRONT);
        //_operatorClimbFront.whenPressed(new MoveClimbPosition(0.0));
        _operatorClimbRear = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CLIMB_REAR);
        //_operatorClimbRear.whenPressed(new ActivateClimbFront(-0.6));
        _operatorClimbAuto = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CLIMB_AUTO);
        _operatorClimbAuto.whenPressed(new MoveClimbPosition(Variables.CLIMB_REAR_LEVEL_THREE));

        _leftJoystickTrigger = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_TRIGGER);
        _leftJoystickFaceBottom = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_FACE_BOTTOM);
        _leftJoystickFaceCenter = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_FACE_CENTER);
        _leftJoystickFaceLeft = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_FACE_LEFT);
        _leftJoystickFaceRight = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_FACE_RIGHT);
        _leftJoystickBaseLeftTop = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_BASE_LEFT_TOP);
        _leftJoystickBaseLeftBottom = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_BASE_LEFT_BOTTOM);
        _leftJoystickBaseBottomLeft = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_BASE_BOTTOM_LEFT);
        _leftJoystickBaseBottomRight = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_BASE_BOTTOM_RIGHT);
        _leftJoystickBaseRightBottom = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_BASE_RIGHT_BOTTOM);
        _leftJoystickBaseRightTop = new JoystickButton(_driverJoystickLeft, Addresses.ATTACK_BASE_RIGHT_TOP);

        _rightJoystickTrigger = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_TRIGGER);
        _rightJoystickThumbButton = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_THUMB_BUTTON);
        _rightJoystickThumbButton.whenPressed(new Interrupt());
        _rightJoystickThumbButton.whenPressed(new FollowOneTarget());
        _rightJoystickFaceBottomLeft = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_FACE_BOTTOM_LEFT);
        _rightJoystickFaceBottomRight = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_FACE_BOTTOM_RIGHT);
        _rightJoystickFaceTopLeft = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_FACE_TOP_LEFT);
        _rightJoystickFaceTopRight = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_FACE_TOP_RIGHT);
        _rightJoystickSeven = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_BASE_SEVEN);
        _rightJoystickEight = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_BASE_EIGHT);
        _rightJoystickNine = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_BASE_NINE);
        _rightJoystickTen = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_BASE_TEN);
        _rightJoystickEleven = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_BASE_ELEVEN);
        _rightJoystickTwelve = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_BASE_TWELVE);

        _opJoystickTrigger = new JoystickButton(_operatorJoystick, Addresses.EXTREME_TRIGGER);
        _opJoystickThumbButton = new JoystickButton(_operatorJoystick, Addresses.EXTREME_THUMB_BUTTON);
        _opJoystickThumbButton.whenPressed(new ZeroSystems());
        _opJoystickThumbButton.whenReleased(new Interrupt());
        _opJoystickFaceBottomLeft = new JoystickButton(_operatorJoystick, Addresses.EXTREME_FACE_BOTTOM_LEFT);
        _opJoystickFaceBottomLeft.whenPressed(new Interrupt());
        _opJoystickFaceBottomLeft.whenPressed(new WristWithJoystick());
        _opJoystickFaceBottomRight = new JoystickButton(_operatorJoystick, Addresses.EXTREME_FACE_BOTTOM_RIGHT);
        _opJoystickFaceTopLeft = new JoystickButton(_operatorJoystick, Addresses.EXTREME_FACE_TOP_LEFT);
        _opJoystickFaceTopLeft.whenPressed(new Interrupt());
        _opJoystickFaceTopLeft.whenPressed(new ElevatorWithJoystick());
        _opJoystickFaceTopRight = new JoystickButton(_operatorJoystick, Addresses.EXTREME_FACE_TOP_RIGHT);
        _opJoystickFaceTopRight.whenPressed(new Interrupt());
        _opJoystickFaceTopRight.whenPressed(new MastWithJoystick());
        _opJoystickSeven = new JoystickButton(_operatorJoystick, Addresses.EXTREME_BASE_SEVEN);
        _opJoystickEight = new JoystickButton(_operatorJoystick, Addresses.EXTREME_BASE_EIGHT);
        _opJoystickNine = new JoystickButton(_operatorJoystick, Addresses.EXTREME_BASE_NINE);
        _opJoystickTen = new JoystickButton(_operatorJoystick, Addresses.EXTREME_BASE_TEN);
        _opJoystickEleven = new JoystickButton(_operatorJoystick, Addresses.EXTREME_BASE_ELEVEN);
        _opJoystickEleven.whenPressed(new Interrupt());
        _opJoystickEleven.whenPressed(new ClimbFrontWithJoystick());
        _opJoystickTwelve = new JoystickButton(_operatorJoystick, Addresses.EXTREME_BASE_TWELVE);
        _opJoystickTwelve.whenPressed(new Interrupt());
        _opJoystickTwelve.whenPressed(new ClimbRearWithJoystick());
        
        Arrays.fill(stopArray, 0.0);
    }

    public static OI getInstance() {
        if (_instance == null) {
            _instance = new OI();
        }
        return _instance;
    }

    // generic things

    public boolean getGenericButton(int joystick, int button) {
        switch (joystick) {
            case Addresses.CONTROLLER_OPERATOR_BLACK:
                return _operatorControllerBlack.getRawButton(button);
            case Addresses.CONTROLLER_OPERATOR_WHITE:
                return _operatorControllerWhite.getRawButton(button);
            case Addresses.CONTROLLER_DRIVER_STICK_LEFT:
                return _driverJoystickLeft.getRawButton(button);
            case Addresses.CONTROLLER_DRIVER_STICK_RIGHT:
                return _driverJoystickRight.getRawButton(button);
            case Addresses.CONTROLLER_OPERATOR_JOYSTICK:
                return _operatorJoystick.getRawButton(button);
            default:
                return false;
        }
    }

    // DRIVER

    public double[] getJoystickInput() {
        _xSpeed = getLeftXInputJoystick();
        _ySpeed = getLeftYInputJoystick();
        _zRotation = -_driverJoystickRight.getRawAxis(Addresses.EXTREME_ROT_AXIS);
        _gyroAngle = IMU.getInstance().getFusedHeading();

        return new double[] { _xSpeed, _ySpeed, _zRotation, _gyroAngle };
    }

    public boolean isHeadless() {
        return false;
    }

    public boolean isForwardOnlyMode() {
        return false;
    }

    // button abstraction so we don't have to change 9.5 instances

    public boolean getElevatorJoystickButton() {
        return getOpStickFaceTopLeft();
    }

    public boolean getMastJoystickButton() {
        return getOpStickFaceTopRight();
    }

    public boolean getWristJoystickButton() {
        return getOpStickFaceBottomLeft();
    }

    public boolean getClimbFrontJoystickButton() {
        return getOpStickEleven();
    }

    public boolean getClimbRearJoystickButton() {
        return getOpStickTwelve();
    }

    // LEFT DRIVER JOYSTICK

    public double getLeftXInputJoystick() {
        return _driverJoystickLeft.getRawAxis(Addresses.ATTACK_X_AXIS);
    }

    public double getLeftYInputJoystick() {
        return _driverJoystickLeft.getRawAxis(Addresses.ATTACK_Y_AXIS);
    }
    
    public boolean getLeftStickTrigger() {
        return _leftJoystickTrigger.get();
    }

    public boolean getLeftJoystickFaceBottom() {
        return _leftJoystickFaceBottom.get();
    }

    public boolean getLeftJoystickFaceCenter() {
        return _leftJoystickFaceCenter.get();
    }

    public boolean getLeftJoystickFaceLeft() {
        return _leftJoystickFaceLeft.get();
    }

    public boolean getLeftJoystickFaceRight() {
        return _leftJoystickFaceRight.get();
    }

    public boolean getLeftJoystickBaseLeftTop() {
        return _leftJoystickBaseLeftTop.get();
    }

    public boolean getLeftJoystickBaseLeftBottom() {
        return _leftJoystickBaseLeftBottom.get();
    }

    public boolean getLeftJoystickBaseBottomLeft() {
        return _leftJoystickBaseBottomLeft.get();
    }

    public boolean getLeftJoystickBaseBottomRight() {
        return _leftJoystickBaseBottomRight.get();
    }

    public boolean getLeftJoystickBaseRightBottom() {
        return _leftJoystickBaseRightBottom.get();
    }

    public boolean getLeftJoystickBaseRightTop() {
        return _leftJoystickBaseRightTop.get();
    }

    // RIGHT DRIVER JOYSTICK

    public double getRightYInputJoystick() {
        return _driverJoystickRight.getRawAxis(Addresses.EXTREME_Y_AXIS);
    }

    public boolean getRightStickTrigger() {
        return _rightJoystickTrigger.get();
    }

    public boolean getRightStickThumbButton() {
        return _rightJoystickThumbButton.get();
    }

    public boolean getRightStickFaceBottomLeft() {
        return _rightJoystickFaceBottomLeft.get();
    }

    public boolean getRightStickFaceBottomRight() {
        return _rightJoystickFaceBottomRight.get();
    }

    public boolean getRightStickFaceTopLeft() {
        return _rightJoystickFaceTopLeft.get();
    }

    public boolean getRightStickFaceTopRight() {
        return _rightJoystickFaceTopRight.get();
    }

    public boolean getRightStickSeven() {
        return _rightJoystickSeven.get();
    }

    public boolean getRightStickEight() {
        return _rightJoystickEight.get();
    }

    public boolean getRightStickNine() {
        return _rightJoystickNine.get();
    }

    public boolean getRightStickTen() {
        return _rightJoystickTen.get();
    }

    public boolean getRightStickEleven() {
        return _rightJoystickEleven.get();
    }

    public boolean getRightStickTwelve() {
        return _rightJoystickTwelve.get();
    }

    // OPERATOR JOYSTICK

    public double getOperatorJoystickX() {
        double axis = _operatorJoystick.getRawAxis(Addresses.EXTREME_X_AXIS);

        if (Math.abs(axis) < Variables.DEADBAND_OPERATORSTICK) {
            axis = 0;
        }

        return axis;
    }

    public double getOperatorJoystickY() {
        double axis = -_operatorJoystick.getRawAxis(Addresses.EXTREME_Y_AXIS);

        if (Math.abs(axis) < Variables.DEADBAND_OPERATORSTICK) {
            axis = 0;
        }

        return axis;
    }

    public boolean getOpStickTrigger() {
        return _opJoystickTrigger.get();
    }

    public boolean getOpStickThumbButton() {
        return _opJoystickThumbButton.get();
    }

    public boolean getOpStickFaceBottomLeft() {
        return _opJoystickFaceBottomLeft.get();
    }

    public boolean getOpStickFaceBottomRight() {
        return _opJoystickFaceBottomRight.get();
    }

    public boolean getOpStickFaceTopLeft() {
        return _opJoystickFaceTopLeft.get();
    }

    public boolean getOpStickFaceTopRight() {
        return _opJoystickFaceTopRight.get();
    }

    public boolean getOpStickSeven() {
        return _opJoystickSeven.get();
    }

    public boolean getOpStickEight() {
        return _opJoystickEight.get();
    }

    public boolean getOpStickNine() {
        return _opJoystickNine.get();
    }

    public boolean getOpStickTen() {
        return _opJoystickTen.get();
    }

    public boolean getOpStickEleven() {
        return _opJoystickEleven.get();
    }

    public boolean getOpStickTwelve() {
        return _opJoystickTwelve.get();
    }

    // white op board

    public boolean getOperatorWhiteOne() {
        return _operatorWhiteOne.get();
    }

    public boolean getOperatorWhiteTwo() {
        return _operatorWhiteTwo.get();
    }

    public boolean getOperatorWhiteThree() {
        return _operatorWhiteThree.get();
    }

    public boolean getOperatorWhiteFour() {
        return _operatorWhiteFour.get();
    }

    public boolean getOperatorWhiteFive() {
        return _operatorWhiteFive.get();
    }

    public boolean getOperatorWhiteSix() {
        return _operatorWhiteSix.get();
    }

    public boolean getOperatorWhiteSeven() {
        return _operatorWhiteSeven.get();
    }

    public boolean getOperatorWhiteEight() {
        return _operatorWhiteEight.get();
    }

    // black op board
    
    public boolean getOperatorBlackOne() {
        return _operatorBlackOne.get();
    }

    public boolean getOperatorBlackTwo() {
        return _operatorBlackTwo.get();
    }

    public boolean getOperatorBlackThree() {
        return _operatorBlackThree.get();
    }

    public boolean getOperatorBlackFour() {
        return _operatorBlackFour.get();
    }

    public boolean getOperatorBlackFive() {
        return _operatorBlackFive.get();
    }

    public boolean getOperatorBlackSix() {
        return _operatorBlackSix.get();
    }

    public boolean getOperatorBlackSeven() {
        return _operatorBlackSeven.get();
    }

    public boolean getOperatorBlackEight() {
        return _operatorBlackEight.get();
    }

    public boolean getOperatorClimbFront() {
        return _operatorClimbFront.get();
    }

    public boolean getOperatorClimbRear() {
        return _operatorClimbRear.get();
    }

    public boolean getOperatorClimbAuto() {
        return _operatorClimbAuto.get();
    }

    public int convertDegreesToIntake(int degrees) {
        return (-degrees * 100000 / 9);
    }

    // PID

    public double fixArcTangent(double angle, double x, double y) { // fix an angle output by arctan
        if (y >= 0) { // apparently y is positive when pointing down right now. not entirely sure why,
                      // but... yeah
            if (x > 0) {
                angle -= 90;
            } else {
                angle = 180 - angle;
            }
        }
        return angle;
    }

    private void logErrorForIntegral(int system, double error) {
        int i;

        for (i = 1; i < NUM_LOG_ENTRIES - 1; i++) { // shift the error log, the oldest entries are the higher numbers
            errorLog[system][i] = errorLog[system][i - 1];
        }

        errorLog[system][0] = error; // log the newest error

        for (i = 0; i < NUM_LOG_ENTRIES; i++) { // get the error sum for the system
            errorSum[system] += errorLog[system][i];
        }
    }

    private void debugMessages(int system, double current, double error, double target, double output) {
        switch (system) {
            case ELEVATOR_SYSTEM:
                SmartDashboard.putNumber("Vertical Lift Error", error);
                SmartDashboard.putNumber("Vertical Lift PID Output", output);
                SmartDashboard.putNumber("Vertical Lift PID Target", target);
                break;
            case MAST_SYSTEM:
                SmartDashboard.putNumber("Horizontal Lift Error", error);
                SmartDashboard.putNumber("Horizontal Lift PID Output", output);
                SmartDashboard.putNumber("Horizontal Lift PID Target", target);
                break;
            case CLIMB_SYSTEM:
                SmartDashboard.putNumber("Climb Error", error);
                SmartDashboard.putNumber("Climb PID Output", output);
                SmartDashboard.putNumber("Climb PID Target", target);
                break;
            case CLIMB_POS_SYSTEM:
                SmartDashboard.putNumber("Climb Pos Error", error);
                SmartDashboard.putNumber("Climb Pos PID Output", output);
                SmartDashboard.putNumber("Climb Pos PID Target", target);
                break;
            case CLIMB_PITCH_SYSTEM:
                SmartDashboard.putNumber("Climb Pitch Error", error);
                SmartDashboard.putNumber("Climb Pitch PID Output", output);
                SmartDashboard.putNumber("Climb Pitch PID Target", target);
                break;
            case INTAKE_SYSTEM:
                SmartDashboard.putNumber("Intake Error", error);
                SmartDashboard.putNumber("Intake PID Output", output);
                SmartDashboard.putNumber("Intake PID Target", target);
            default:
                break;
        }
    }

    public boolean checkIfNeedBeRun(int system, double error, double speed) {
        switch (system) {
            case ROT_SYSTEM:
                if (DriveTrain.getInstance().getTurnInProgress() && Math.abs(error) < 3.0) {
                    DriveTrain.getInstance().setTurnInProgress(false);
                    return false;
                }
                return true;
            case ELEVATOR_SYSTEM:
                if (Math.abs(error) < 100) {
                    Elevator.getInstance().changeAdjustingBool(false);
                    return false;
                }
                return true;
            case MAST_SYSTEM:
                if (Math.abs(error) < 1000) {
                    Mast.getInstance().changeAdjustingBool(false);
                    return false;
                }
                return true;
            case VISION_X_SYSTEM:
                if (Math.abs(error) < 5) {
                    return false;
                }
                return true;
            case VISION_Y_SYSTEM:
                if (Math.abs(error) < 10) {
                    return false;
                }
                return true;
            case VISION_ROT_SYSTEM:
                if (DriveTrain.getInstance().getTurnInProgress() && Math.abs(error) < 3.0) {
                    DriveTrain.getInstance().setTurnInProgress(false);
                    return false;
                }
                return true;
            case CLIMB_POS_SYSTEM:
                if (Math.abs(error) < 1.0) {
                    if (ClimbRear.getInstance().getBothAdjustingBool()) { // bothadjusting takes priority
                        ClimbRear.getInstance().setAdjustingBool(false);
                    } else if (ClimbFront.getInstance().getAdjustingBool()) { // then front
                        ClimbFront.getInstance().setAdjustingBool(false);
                    } else if (ClimbRear.getInstance().getAdjustingBool()) { // then rear
                        ClimbFront.getInstance().setAdjustingBool(false);
                    }
                    return false;
                }
                return true;
            case CLIMB_PITCH_SYSTEM:
                if (Math.abs(error) < 1.5) {
                    return false;
                }
                return true;
            case INTAKE_SYSTEM:
                if (Math.abs(error) < 10000) {
                    Intake.getInstance().changeAdjustingBool(false);
                    return false;
                }
            default: // we probably don't even need a default case lmao
                return true;
        }
    }

    public double applyPID(int system, double current, double target, double kP, double kI, double kD) {
        return applyPID(system, current, target, kP, kI, kD, 0.0, 0.0);
    }

    /*
     * formula for output at time t (o(t)), based on error at time t (e(t)): o(t) =
     * (kP * e(t)) + (kI * ∫e(t)dt) - (kD * (de(t) / dt)) , because we can't d s
     * mbolic integration or derivation: o(t) = kP(instant error) + kI(total error)
     * - kD(instant change in error)
     */
    public double applyPID(int system, double current, double target, double kP, double kI, double kD, double outputMax, double outputMin) {
        double output;
        double termP, termI, termD;
        double error = target - current;

        // the proportional stuff just kinda exists, the initial correction
        termP = kP * error;

        // initialize each system
        if (firstRun[system]) {
            lastActual[system] = current;
            firstRun[system] = false;
        }

        if (!checkIfNeedBeRun(system, error, lastOutput[system])) {
            return 0.0;
        }

        // slow down correction if it's doing the right thing (in an effort to prevent major overshooting)
        // formula: -kD * change in read, "change in read" being the instant derivative at that point in time
        termD = -kD * (current - lastActual[system]);
        lastActual[system] = current;

        // because the I term is the area under the curve, it gets a higher weight if it's been going on for a longer time, hence the errorSum
        // formula: kI * errorSum (sum of all previous errors)
        termI = kI * errorSum[system];

        // finally add everything together
        output = termP + termI + termD;

        if (outputMax != outputMin) { // if we decide to use mins/maxes on outputs
            if (output > outputMax) {
                output = outputMax;
            } else if (output < outputMin) {
                output = outputMin;
            }
        }

        lastOutput[system] = output; // log the last output for speed checking purposes

        logErrorForIntegral(system, error); // log the new error for the integral

        debugMessages(system, current, error, target, output); // made a new method so as not to clog up this method

        return output;
    }

}