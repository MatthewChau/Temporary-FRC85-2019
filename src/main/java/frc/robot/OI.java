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
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.intake.ActivateIntake;
import frc.robot.commands.intake.ActivateWrist;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.intake.WristWithJoystick;
import frc.robot.commands.lift.ActivateMast;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.MastWithJoystick;
import frc.robot.commands.lift.ElevatorWithJoystick;
import frc.robot.commands.spike.SetSpike;
import frc.robot.commands.spike.ToggleSpike;
import frc.robot.commands.climb.ActivateClimbRear;
import frc.robot.commands.climb.ActivateClimbRearDrive;
import frc.robot.commands.climb.ActivateClimbFront;
import frc.robot.commands.climb.ClimbFrontWithJoystick;
import frc.robot.commands.climb.ClimbRearLock;
import frc.robot.commands.climb.ClimbRearWithJoystick;
import frc.robot.commands.climb.setClimbRearLock;
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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    private static OI _instance;

    private Joystick _driverController, _driverJoystickRight, _driverJoystickLeft;
    private Joystick _operatorControllerWhite, _operatorControllerBlack, _operatorJoystick;

    private JoystickButton _controllerAButton, _controllerBButton, _controllerXButton, _controllerYButton,
            _controllerLeftBumper, _controllerRightBumper, _controllerBackButton, _controllerStartButton,
            _controllerLeftStickIn, _controllerRightStickIn;

    private JoystickButton _rightJoystickTrigger, _rightJoystickThumbButton, _rightJoystickFaceBottomLeft,
            _rightJoystickFaceBottomRight, _rightJoystickFaceTopLeft, _rightJoystickFaceTopRight, _rightJoystickSeven,
            _rightJoystickEight, _rightJoystickNine, _rightJoystickTen, _rightJoystickEleven, _rightJoystickTwelve;

    private JoystickButton _leftJoystickTrigger, _leftJoystickFaceBottom, _leftJoystickFaceCenter,
            _leftJoystickFaceLeft, _leftJoystickFaceRight, _leftJoystickBaseLeftTop, _leftJoystickBaseLeftBottom,
            _leftJoystickBaseBottomLeft, _leftJoystickBaseBottomRight, _leftJoystickBaseRightBottom,
            _leftJoystickBaseRightTop;

    private JoystickButton _opJoystickTrigger, _opJoystickFaceBottom, _opJoystickFaceCenter, _opJoystickFaceLeft,
            _opJoystickFaceRight, _opJoystickBaseLeftTop, _opJoystickBaseLeftBottom, _opJoystickBaseBottomLeft,
            _opJoystickBaseBottomRight, _opJoystickBaseRightBottom, _opJoystickBaseRightTop;

    // White
    private JoystickButton _operatorCargoShip, _operatorCargoFloor, _operatorRollerOut, _operatorCargoOne,
            _operatorCargoTwo, _operatorCargoThree, _operatorClimbFront, _operatorClimbRear, _operatorElevator,
            _operatorRollerIn;
    // Black
    private JoystickButton _operatorHatchStation, _operatorHatchFloor, _operatorHatchRelease, _operatorHatchOne,
            _operatorHatchTwo, _operatorHatchThree, _operatorClimbAuto, _operatorMast, _operatorWrist;

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

        // black
        _operatorMast = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_MAST);
        _operatorMast.whenPressed(new Interrupt());
        _operatorMast.whenPressed(new MastWithJoystick());
        _operatorWrist = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_WRIST);
        _operatorWrist.whenPressed(new Interrupt());
        _operatorWrist.whenPressed(new WristWithJoystick());
        _operatorHatchStation = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_STATION);
        _operatorHatchStation.whenPressed(new Place(Variables.HATCH_STATION, Variables.WRIST_30, Variables.MAST_FORWARD_POS));
        _operatorHatchStation.whenReleased(new Place((Variables.HATCH_ONE + 500), Variables.WRIST_0, Variables.MAST_CURRENT_POS));
        _operatorHatchRelease = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_RELEASE);
        _operatorHatchRelease.whenPressed(new Place(Variables.ELEVATOR_CURRENT_POS, Variables.WRIST_30, (Variables.MAST_FORWARD_POS - 100000)));
        _operatorHatchRelease.whenReleased(new Place(Variables.ELEVATOR_CURRENT_POS, Variables.WRIST_CURR_POSITION, Variables.MAST_PROTECTED));
        _operatorHatchFloor = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_FLOOR);
        _operatorHatchFloor.whenPressed(new HatchGroundOne());
        _operatorHatchFloor.whenReleased(new HatchGroundTwo());
        _operatorHatchThree = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_THREE);
        _operatorHatchThree.whenPressed(new Place(Variables.HATCH_THREE, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
        _operatorHatchThree.whenReleased(new Place(Variables.HATCH_THREE, Variables.WRIST_0, Variables.MAST_CURRENT_POS));
        _operatorHatchTwo = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_TWO);
        _operatorHatchTwo.whenPressed(new Place(Variables.HATCH_TWO, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
        _operatorHatchTwo.whenReleased(new Place(Variables.HATCH_TWO, Variables.WRIST_0, Variables.MAST_CURRENT_POS));
        //_operatorHatchThree.whenPressed(new ActivateClimbFront(0.3));
        //_operatorHatchThree.whenReleased(new ActivateClimbFront(0.0));
        //_operatorHatchTwo.whenPressed(new ActivateClimbFront(-0.3));
        //_operatorHatchTwo.whenReleased(new ActivateClimbFront(0.0));
        _operatorHatchOne = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_ONE);
        _operatorHatchOne.whenPressed(new Place(Variables.HATCH_ONE, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
        _operatorHatchOne.whenReleased(new Place(Variables.HATCH_ONE, Variables.WRIST_0, Variables.MAST_CURRENT_POS));

        // white
        _operatorElevator = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_ELEVATOR);
        _operatorElevator.whenPressed(new Interrupt());
        _operatorElevator.whenPressed(new ElevatorWithJoystick());
        _operatorRollerIn = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_ROLLER_IN);
        _operatorRollerIn.whenPressed(new ActivateIntake(Variables.ROLLER_IN));
        _operatorRollerIn.whenReleased(new ActivateIntake(0));
        _operatorCargoShip = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_SHIP);
        _operatorCargoShip.whenPressed(new Place(Variables.CARGO_SHIP, Variables.WRIST_90, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorCargoShip.whenPressed(new Place(Variables.CARGO_SHIP, Variables.WRIST_90, Variables.MAST_CURRENT_POS));
        _operatorRollerOut = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_OUT);
        _operatorRollerOut.whenPressed(new ActivateIntake(Variables.ROLLER_OUT));
        _operatorRollerOut.whenReleased(new ActivateIntake(0));
        _operatorCargoFloor = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_FLOOR);
        _operatorCargoFloor.whenPressed(new CargoGroundOne());
        _operatorCargoFloor.whenReleased(new CargoGroundTwo());
        _operatorCargoThree = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_THREE);
        _operatorCargoThree.whenPressed(new Place(Variables.CARGO_THREE, Variables.WRIST_CARGO_HIGH, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorCargoThree.whenReleased(new Place(Variables.CARGO_THREE, Variables.WRIST_CARGO_HIGH, Variables.MAST_CURRENT_POS));
        _operatorCargoTwo = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_TWO);
        _operatorCargoTwo.whenPressed(new Place(Variables.CARGO_TWO, Variables.WRIST_CARGO, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorCargoTwo.whenReleased(new Place(Variables.CARGO_TWO, Variables.WRIST_CARGO, Variables.MAST_CURRENT_POS));
        //_operatorCargoThree.whenPressed(new ActivateClimbRear(0.3));
        //_operatorCargoThree.whenReleased(new ActivateClimbRear(0.0));
        //_operatorCargoTwo.whenPressed(new ActivateClimbRear(-0.3));
        //_operatorCargoTwo.whenReleased(new ActivateClimbRear(0.0));
        _operatorCargoOne = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_ONE);
        //_operatorCargoOne = new JoystickButton(_operatorJoystick, AddressesOpJoystick.OPERATOR_CARGO_ONE);
        _operatorCargoOne.whenPressed(new Place(Variables.CARGO_ONE, Variables.WRIST_CARGO, Variables.MAST_FORWARD_FOR_CARGO));
        _operatorCargoOne.whenReleased(new Place(Variables.CARGO_ONE, Variables.WRIST_CARGO, Variables.MAST_CURRENT_POS));

        // the thing off to the side
        _operatorClimbFront = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_CLIMB_FRONT);
        // _operatorClimbFront.whenPressed(new ClimbFrontWithJoystick());
        _operatorClimbFront.whenPressed(new MoveClimbPosition(0.0));
        _operatorClimbRear = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CLIMB_REAR);
        // _operatorClimbRear.whenPressed(new ClimbRearWithJoystick());
        _operatorClimbRear.whenPressed(new ActivateClimbFront(-0.6));
        _operatorClimbAuto = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CLIMB_AUTO);
        // _operatorClimbAuto.whenPressed(new setClimbRearLock());
        _operatorClimbAuto.whenPressed(new MoveClimbPosition(Variables.CLIMB_REAR_LEVEL_THREE / 2));

        /*
         * _driverController = new Joystick(Addresses.CONTROLLER_DRIVER); // drive
         * controller
         * 
         * _controllerAButton = new JoystickButton(_driverController,
         * Addresses.A_BUTTON); _controllerBButton = new
         * JoystickButton(_driverController, Addresses.B_BUTTON); _controllerXButton =
         * new JoystickButton(_driverController, Addresses.X_BUTTON); _controllerYButton
         * = new JoystickButton(_driverController, Addresses.Y_BUTTON);
         * _controllerLeftBumper = new JoystickButton(_driverController,
         * Addresses.LEFT_BUMPER); _controllerRightBumper = new
         * JoystickButton(_driverController, Addresses.RIGHT_BUMPER);
         * _controllerBackButton = new JoystickButton(_driverController,
         * Addresses.BACK_BUTTON); _controllerStartButton = new
         * JoystickButton(_driverController, Addresses.START_BUTTON);
         * _controllerLeftStickIn = new JoystickButton(_driverController,
         * Addresses.LEFT_STICK_IN); _controllerRightStickIn = new
         * JoystickButton(_driverController, Addresses.RIGHT_STICK_IN);
         */

        _driverJoystickLeft = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_LEFT); // logitech attack

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

        _driverJoystickRight = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_RIGHT); // logitech extreme 3d

        _rightJoystickTrigger = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_TRIGGER);
        _rightJoystickThumbButton = new JoystickButton(_driverJoystickRight, Addresses.EXTREME_THUMB_BUTTON);
        _rightJoystickThumbButton.whileHeld(new ActivateClimbRearDrive(0.5));
        _rightJoystickThumbButton.whenReleased(new ActivateClimbRearDrive(0.0));
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

        _operatorJoystick = new Joystick(Addresses.CONTROLLER_OPERATOR_JOYSTICK); // logitech attack

        _opJoystickTrigger = new JoystickButton(_operatorJoystick, Addresses.ATTACK_TRIGGER);
        _opJoystickFaceBottom = new JoystickButton(_operatorJoystick, Addresses.ATTACK_FACE_BOTTOM);
        _opJoystickFaceCenter = new JoystickButton(_operatorJoystick, Addresses.ATTACK_FACE_CENTER);
        _opJoystickFaceLeft = new JoystickButton(_operatorJoystick, Addresses.ATTACK_FACE_LEFT);
        _opJoystickFaceRight = new JoystickButton(_operatorJoystick, Addresses.ATTACK_FACE_RIGHT);
        _opJoystickBaseLeftTop = new JoystickButton(_operatorJoystick, Addresses.ATTACK_BASE_LEFT_TOP);
        _opJoystickBaseLeftBottom = new JoystickButton(_operatorJoystick, Addresses.ATTACK_BASE_LEFT_BOTTOM);
        _opJoystickBaseBottomLeft = new JoystickButton(_operatorJoystick, Addresses.ATTACK_BASE_BOTTOM_LEFT);
        _opJoystickBaseBottomRight = new JoystickButton(_operatorJoystick, Addresses.ATTACK_BASE_BOTTOM_RIGHT);
        _opJoystickBaseRightBottom = new JoystickButton(_operatorJoystick, Addresses.ATTACK_BASE_RIGHT_BOTTOM);
        _opJoystickBaseRightTop = new JoystickButton(_operatorJoystick, Addresses.ATTACK_BASE_RIGHT_TOP);
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

    public double[] getControllerInput() {
        _xSpeed = getXInputController();
        _ySpeed = getYInputController();
        _zRotation = -_driverController.getRawAxis(Addresses.RIGHT_X_AXIS);
        _gyroAngle = IMU.getInstance().getFusedHeading();

        return new double[] { _xSpeed, _ySpeed, _zRotation, _gyroAngle };
    }

    public double[] getJoystickInput() {
        _xSpeed = getXInputJoystick();
        _ySpeed = getYInputJoystick();
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

    // CONTROLLER

    public double getXInputController() {
        return _driverController.getRawAxis(Addresses.LEFT_X_AXIS);
    }

    public double getYInputController() {
        return _driverController.getRawAxis(Addresses.LEFT_Y_AXIS);
    }

    /*
     * // CONTROLLER BUTTONS
     * 
     * public boolean getAButton() { return _controllerAButton.get(); }
     * 
     * public boolean getBButton() { return _controllerBButton.get(); }
     * 
     * public boolean getXButton() { return _controllerXButton.get(); }
     * 
     * public boolean getYButton() { return _controllerYButton.get(); }
     * 
     * public boolean getRightBumper() { return _controllerRightBumper.get(); }
     */

    // JOYSTICKS

    public double getXInputJoystick() {
        return _driverJoystickLeft.getRawAxis(Addresses.ATTACK_X_AXIS);
    }

    public double getYInputJoystick() {
        return _driverJoystickLeft.getRawAxis(Addresses.ATTACK_Y_AXIS);
    }

    // JOYSTICKS BUTTONS

    public boolean getLeftStickTrigger() {
        return _leftJoystickTrigger.get();
    }

    public boolean getTurn180Button() {
        return _leftJoystickFaceBottom.get();
    }

    public boolean getTurnLeft90() {
        return _leftJoystickFaceLeft.get();
    }

    public boolean getTurnRight90() {
        return _leftJoystickFaceRight.get();
    }

    public boolean getRightStickTrigger() {
        return _rightJoystickTrigger.get();
    }

    public boolean getForwardBackwardOnlyButton() {
        return _rightJoystickTwelve.get();
    }

    public boolean getDriverThumbButton() {
        return _rightJoystickThumbButton.get();
    }

    // OPERATOR JOYSTICK
    public double getOperatorJoystickX() {
        double axis = _operatorJoystick.getRawAxis(Addresses.ATTACK_X_AXIS);

        if (Math.abs(axis) < Variables.DEADBAND_OPERATORSTICK) {
            axis = 0;
        }

        return axis;
    }

    public double getOperatorJoystickY() {
        double axis = -_operatorJoystick.getRawAxis(Addresses.ATTACK_Y_AXIS);

        if (Math.abs(axis) < Variables.DEADBAND_OPERATORSTICK) {
            axis = 0;
        }

        return axis;
    }

    public boolean getOperatorLiftHorizontal() {
        return _operatorMast.get();
    }

    public boolean getOperatorLiftVertical() {
        return _operatorElevator.get();
    }

    public boolean getOperatorWristRotate() {
        return _operatorWrist.get();
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

        for (i = 1; i < 4; i++) { // shift the error log, the oldest entries are the higher numbers
            errorLog[system][i] = errorLog[system][i - 1];
        }

        errorLog[system][0] = error; // log the newest error

        for (i = 0; i < 4; i++) { // get the error sum for the system
            errorSum[system] += errorLog[system][i];
        }
    }

    private void debugMessages(int system, double current, double error, double target, double output) {
        switch (system) {
        /*
         * case ROT_SYSTEM: SmartDashboard.putNumber("Rot PID Target", target);
         * SmartDashboard.putNumber("Rot PID Error", error); break;
         */
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
        /*
         * case VISION_X_SYSTEM: SmartDashboard.putNumber("Vision PID Error X", error);
         * SmartDashboard.putNumber("Vision PID Output X", output); break; case
         * VISION_Y_SYSTEM: SmartDashboard.putNumber("Vision PID Target Distance",
         * target); SmartDashboard.putNumber("Vision PID Error Distance", error); break;
         * case VISION_ROT_SYSTEM: SmartDashboard.putNumber("Vision PID Rotation Error",
         * error); SmartDashboard.putNumber("Vision PID Rotation Output", output);
         * break;
         */
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
            if (Math.abs(error) < 5.0) {
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
    public double applyPID(int system, double current, double target, double kP, double kI, double kD, double outputMax,
            double outputMin) {
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