/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.Variables;

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
import frc.robot.commands.driverassistance.Place;
import frc.robot.commands.driverassistance.CargoStation1;
import frc.robot.commands.driverassistance.CargoStation2;
import frc.robot.commands.driverassistance.HatchGround2;
import frc.robot.commands.driverassistance.HatchGround1;
import frc.robot.commands.driverassistance.HatchRelease;
import frc.robot.commands.driverassistance.HatchStationOne;
import frc.robot.commands.driverassistance.HatchStationTwo;
import frc.robot.commands.driverassistance.Interrupt;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    private static OI _instance;

    private Joystick _driverController, _driverJoystickRight, _driverJoystickLeft;
    private Joystick _operatorControllerWhite, _operatorControllerBlack;

    private JoystickButton _driverLeftBumper, _driverControllerAButton, _driverControllerBButton, _driverControllerXButton, _driverControllerYButton;

    // White
    private JoystickButton _operatorCargoDefault, _operatorCargoFloor, _operatorCargoIn, _operatorCargoOut,
        _operatorCargoOne, _operatorCargoTwo, _operatorCargoThree,  
        _operatorElevator,
        _operatorClimbFront, _operatorClimbBack;
    // Black
    private JoystickButton _operatorHatchDefault, _operatorHatchFloor, _operatorHatchRelease, 
        _operatorHatchOne, _operatorHatchTwo, _operatorHatchThree,
        _operatorMast, _operatorWrist,
        _operatorClimbAuto;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;

    private double _gyroAngle;

    public static final int ROT_SYSTEM = 0;
    public static final int ELEVATOR_SYSTEM = 1;
    public static final int MAST_SYSTEM = 2;
    public static final int VISION_X_SYSTEM = 3;
    public static final int VISION_Y_SYSTEM = 4;
    public static final int VISION_ROT_SYSTEM = 5;
    public static final int CLIMB_SYSTEM = 6;
    public static final int INTAKE_SYSTEM = 7;

    private int NUM_LOG_ENTRIES = 5;

    public boolean[] firstRun = new boolean[INTAKE_SYSTEM + 1];
    public double[] errorSum = new double[INTAKE_SYSTEM + 1];
    public double[] lastActual = new double[INTAKE_SYSTEM + 1];
    public double[][] errorLog = new double[INTAKE_SYSTEM + 1][NUM_LOG_ENTRIES];

    public double[] stopArray = new double[4];

    private OI() {
        _driverController = new Joystick(Addresses.CONTROLLER_DRIVER);
        _driverJoystickRight = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_RIGHT);
        _driverJoystickLeft = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_LEFT);
        _operatorControllerWhite = new Joystick(Addresses.CONTROLLER_OPERATOR_WHITE);
        _operatorControllerBlack = new Joystick(Addresses.CONTROLLER_OPERATOR_BLACK);

        _driverControllerAButton = new JoystickButton(_driverController, 1);
        _driverControllerBButton = new JoystickButton(_driverController, 2);
        _driverControllerXButton = new JoystickButton(_driverController, 3);
        _driverControllerYButton = new JoystickButton(_driverController, 4);

        // Joystick combinations
        _operatorElevator = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_LIFT_VERTICAL);
        _operatorElevator.whenPressed(new Interrupt());
        _operatorElevator.whenPressed(new ElevatorWithJoystick());
        _operatorMast = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_LIFT_HORIZONTAL);
        _operatorMast.whenPressed(new Interrupt());
        _operatorMast.whenPressed(new MastWithJoystick());
        _operatorWrist = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_INTAKE_ROTATE);
        _operatorWrist.whenPressed(new Interrupt());
        _operatorWrist.whenPressed(new WristWithJoystick());

        // Cargo
        _operatorCargoDefault = new JoystickButton(_operatorControllerWhite, 3);
        _operatorCargoDefault.whenPressed(new Place(Variables.CARGO_SHIP, Variables.WRIST_POS_FLOOR_PICKUP));
        //_operatorCargoDefault.whenPressed(new CargoStation1());
        //_operatorCargoDefault.whenReleased(new CargoStation2());
        _operatorCargoFloor = new JoystickButton(_operatorControllerWhite, 5);
        _operatorCargoIn = new JoystickButton(_operatorControllerWhite, 2);
        _operatorCargoIn.whenPressed(new ActivateIntake(0.8));
        _operatorCargoIn.whenReleased(new ActivateIntake(0));
        _operatorCargoOut = new JoystickButton(_operatorControllerWhite, 4);
        _operatorCargoOut.whenPressed(new ActivateIntake(-0.8));
        _operatorCargoOut.whenReleased(new ActivateIntake(0));

        _operatorCargoOne = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_ONE);
        _operatorCargoOne.whenPressed(new Place(Variables.CARGO_ONE, Variables.WRIST_ANGLE_FOR_CARGO));
        _operatorCargoTwo = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_TWO);
        _operatorCargoTwo.whenPressed(new Place(Variables.CARGO_TWO, Variables.WRIST_ANGLE_FOR_CARGO));
        _operatorCargoThree = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CARGO_THREE);
        _operatorCargoThree.whenPressed(new Place(Variables.CARGO_THREE, Variables.WRIST_ANGLE_FOR_CARGO));

        // Hatch
        _operatorHatchDefault = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_DEFAULT);
        _operatorHatchDefault.whenPressed(new HatchStationOne());
        _operatorHatchDefault.whenReleased(new HatchStationTwo());
        _operatorHatchFloor = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_FLOOR);
        //_operatorHatchFloor.whenPressed(new HatchGround1());
        //_operatorHatchFloor.whenReleased(new HatchGround2());
        _operatorHatchRelease = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_RELEASE);
        _operatorHatchRelease.whenPressed(new HatchRelease());
        //_operatorHatchRelease.whenReleased(new Interrupt());

        _operatorHatchOne = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_ONE);
        _operatorHatchOne.whenPressed(new Place(Variables.HATCH_ONE, Variables.WRIST_0));
        _operatorHatchTwo = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_TWO);
        _operatorHatchTwo.whenPressed(new Place(Variables.HATCH_TWO, Variables.WRIST_0));
        _operatorHatchThree = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_HATCH_THREE);
        _operatorHatchThree.whenPressed(new Place(Variables.HATCH_THREE, Variables.WRIST_0));

        // Climb
        _operatorClimbAuto = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CLIMB_AUTO);
        _operatorClimbBack = new JoystickButton(_operatorControllerBlack, Addresses.OPERATOR_CLIMB_BACK);
        _operatorClimbFront = new JoystickButton(_operatorControllerWhite, Addresses.OPERATOR_CLIMB_FRONT);

        //_operatorHatchDefault.whenPressed(new HatchStation1());
        //_operatorHatchDefault.whenReleased(new HatchStation2());

        //_driverControllerAButton.whenPressed(new HatchGround1());
        //_driverControllerAButton.whenReleased(new Interrupt());

        //FollowOneTarget followOneTarget;
        //_driverControllerYButton.whileActive(followOneTarget = new FollowOneTarget()); //follows when pressed
        
        //FollowTwoTarget followTwoTarget;
        //_driverControllerXButton.whileActive(followTwoTarget = new FollowTwoTarget());

        //_driverControllerAButton.whenPressed(new Place(Variables.getInstance().HATCH_TWO, -500000));
        //_driverControllerBButton.whenPressed(new HatchRelease());
    }

    public static OI getInstance() {
        if (_instance == null) {
            _instance = new OI();
        }
        return _instance;
    }

    public double[] getControllerInput() {
        _xSpeed = getXInputController();
        _ySpeed = getYInputController();
        _zRotation = -_driverController.getRawAxis(4);
        _gyroAngle = IMU.getInstance().getFusedHeading();

        return new double[] {_xSpeed, _ySpeed, _zRotation, _gyroAngle};
    }

    public double[] getJoystickInput() {
        _xSpeed = getXInputJoystick();
        _ySpeed = getYInputJoystick();
        _zRotation = -_driverJoystickRight.getRawAxis(2);
        _gyroAngle = IMU.getInstance().getFusedHeading();

        return new double[] {_xSpeed, _ySpeed, _zRotation, _gyroAngle};
    }

    public double getXInputController() {
        return _driverController.getRawAxis(0);
    }

    public double getYInputController() {
        return _driverController.getRawAxis(1);
    }

    public double getXInputJoystick() {
        return _driverJoystickLeft.getRawAxis(0);
    }

    public double getYInputJoystick() {
        return _driverJoystickLeft.getRawAxis(1);
    }

    public double getBeltInputJoystick() {
        if (Math.abs(_driverJoystickRight.getRawAxis(1)) > Variables.DEADBAND_Z_DRIVERSTICK) {
            return _driverJoystickRight.getRawAxis(1);
        }
        return 0.0;
    }

    public boolean isHeadless() {
        /*if (SmartDashboard.getBoolean("Joysticks Enabled", false)) { 
            return getLeftStickTrigger();
        } else {
            return getRightBumper();
        }*/
        return false;
    }
    
    private boolean getRightBumper() {
        return _driverController.getRawButton(6);
    }
    
    public boolean getLeftStickTrigger() {
        return _driverJoystickLeft.getRawButton(1);
    }

    public boolean getRightStickTrigger() {
        return _driverJoystickRight.getRawButton(1);
    }

    public boolean isForwardOnlyMode() {
        /*if (SmartDashboard.getBoolean("Joysticks Enabled", false)) {
            return getLeftJoystickForwardOnlyMode();
        } else {
            return getAButton();
        }*/
        return false;
    }

    private boolean getAButton() {
        return _driverController.getRawButton(1);
    }

    private boolean getLeftJoystickForwardOnlyMode() {
        return _driverJoystickLeft.getRawButton(2);
    }

    public boolean getBButton() {
        return _driverController.getRawButton(2);
    }

    public boolean getTurnLeft90() {
        //if (SmartDashboard.getBoolean("Joysticks Enabled", false)) {
            return getTurn90LeftButton();
        //} else {
        //    return false;//getXButton();
        //}
    }

    public boolean getTurnRight90() {
        //if (SmartDashboard.getBoolean("Joysticks Enabled", false)) {
            return getTurn90RightButton();
        //} else {
        //    return false;//getBButton();
        //}
    }

    private boolean getXButton() {
        return _driverController.getRawButton(3);
    }

    private boolean getTurn90LeftButton() {
        return _driverJoystickLeft.getRawButton(4);
    }

    public boolean getYButton() {
        return _driverController.getRawButton(4);
    }

    private boolean getTurn90RightButton() {
        return _driverJoystickLeft.getRawButton(5);
    }

    public boolean getTurn180Button() {
        return _driverJoystickLeft.getRawButton(2);
    }

    // Operator Control Board

    public double getOperatorJoystickY() {
        double axis = _operatorControllerBlack.getRawAxis(1);

        if (Math.abs(axis) < Variables.DEADBAND_OPERATORSTICK) {
            axis = 0;
        }

        return axis;
    }

    public double getOperatorJoystickX() {
        double axis = _operatorControllerBlack.getRawAxis(0);

        if (Math.abs(axis) < Variables.DEADBAND_OPERATORSTICK) {
            axis = 0;
        }

        return axis;
    }

    public double getOpStickModifier() {
        double modifier;

        if (getOperatorJoystickX() > 0) {
            modifier = 0.5;
        } else if (getOperatorJoystickX() < 0) {
            modifier = 1.25;
        } else {
            modifier = 1.0;
        }

        return 1.0;
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

    public boolean getOperatorHatchRelease() {
        return _operatorHatchRelease.get();
    }

    public boolean getOperatorCargoDefault() {
        return _operatorCargoDefault.get();
    }

    public int convertDegreesToIntake(int degrees) {
        return (-degrees * 100000 / 9);
    }

    public double fixArcTangent(double angle, double x, double y) { // fix an angle output by arctan
        if (y >= 0) { // apparently y is positive when pointing down right now.  not entirely sure why, but... yeah
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
        switch(system) {
            /*case ROT_SYSTEM:
                SmartDashboard.putNumber("Rot PID Target", target);
                SmartDashboard.putNumber("Rot PID Error", error);
                break;*/
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
            /*case VISION_X_SYSTEM:
                SmartDashboard.putNumber("Vision PID Error X", error);
                SmartDashboard.putNumber("Vision PID Output X", output);
                break;
            case VISION_Y_SYSTEM:
                SmartDashboard.putNumber("Vision PID Target Distance", target);
                SmartDashboard.putNumber("Vision PID Error Distance", error);
                break;
            case VISION_ROT_SYSTEM:
                SmartDashboard.putNumber("Vision PID Rotation Error", error);
                SmartDashboard.putNumber("Vision PID Rotation Output", output);
                break;*/
            case CLIMB_SYSTEM:
                SmartDashboard.putNumber("Climb Error", error);
                SmartDashboard.putNumber("Climb PID Output", output);
                SmartDashboard.putNumber("Climb PID Target", target);
                break;
            case INTAKE_SYSTEM:
                SmartDashboard.putNumber("Intake Error", error);
                SmartDashboard.putNumber("Intake PID Output", output);
                SmartDashboard.putNumber("Intake PID Target", target);
            default:
                break;
        }
    }

    public boolean checkIfNeedBeRun(int system, double error) {
        switch (system) {
            case ROT_SYSTEM:
                if (DriveTrain.getInstance().getTurnInProgress() && Math.abs(error) < 3.0) { // note that we actually want a tolerance here
                    DriveTrain.getInstance().setTurnInProgress(false);
                    return false;
                }
                return true;
            case ELEVATOR_SYSTEM:
                if (Math.abs(error) < 450) {
                    Elevator.getInstance().changeAdjustingBool(false);
                }
                return true;
            case MAST_SYSTEM:
                if (Math.abs(error) < 1000) { // this value definitely subject to change
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
    formula for output at time t (o(t)), based on error at time t (e(t)):
    o(t) = (kP * e(t)) + (kI * ∫e(t)dt) - (kD * (de(t) / dt))
    or, because we can't do symbolic integration or derivation:
    o(t) = kP(instant error) + kI(total error) - kD(instant change in error)
    */
    public double applyPID(int system, double current, double target, double kP, double kI, double kD, double outputMax, double outputMin) {
        double output;
        double termP, termI, termD;
        double error = target - current;

        if (!checkIfNeedBeRun(system, error)) {
            return 0.0;
        }

        // the proportional stuff just kinda exists, the initial correction
        termP = kP * error;

        if (firstRun[system]) {
            lastActual[system] = current;
            firstRun[system] = false;
        }

        // slow down correction if it's doing the right thing (in an effort to prevent major overshooting)
        // formula:  -kD * change in read, "change in read" being the instant derivative at that point in time
        termD = -kD * (current - lastActual[system]);
        lastActual[system] = current;

        // because the I term is the area under the curve, it gets a higher weight if it's been going on for a longer time, hence the errorSum
        // formula:  kI * errorSum (sum of all previous errors)
        termI = kI * errorSum[system];

        output = termP + termI + termD;
        
        if (outputMax != outputMin) { // if we decide to use mins/maxes on outputs, then we can
            if (output > outputMax) {
                output = outputMax;
            } else if (output < outputMin) {
                output = outputMin;
            }
        }

        logErrorForIntegral(system, error);

        debugMessages(system, current, error, target, output); // made a new method so as not to clog up this method

        return output;
    }
    
}