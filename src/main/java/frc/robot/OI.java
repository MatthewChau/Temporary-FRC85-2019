/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LiftVertical;
import frc.robot.subsystems.LiftHorizontal;
import frc.robot.sensors.IMU;

import frc.robot.commands.drivetrain.FollowOneTarget;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.lift.HorizontalShift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.lift.HorizontalShift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    private static OI _instance;

    private Joystick _driverController, _driverJoystickRight, _driverJoystickLeft;
    private Joystick _operatorController1, _operatorController2;

    private JoystickButton _driverLeftBumper, _driverControllerAButton, _driverControllerBButton, _driverControllerXButton, _driverControllerYButton;
    private JoystickButton _operatorPhaseZero, _operatorPhaseOne, _operatorPhaseTwo, _operatorPhaseThree, _operatorLeftBumper, _operatorRightBumper;
    private JoystickButton _operatorCargoPhaseOne, _operatorLiftDown, _operatorCargoPhaseTwo, _operatorCargoPhaseThree, _operatorHatchPhaseOne, _operatorHatchPhaseTwo, _operatorHatchPhaseThree, 
        _operatorLiftUpAndDown, _operatorIntakeRotate, _operatorMastBackAndForth, _operatorCargoIn, _operatorCargoLSOCS, _operatorCargoOut, _operatorCargoFloor, _operatorHatchLSOCS, 
        _operatorHatchFloor, _operatorHatchRelease;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;

    private double _gyroAngle;

    public static final int ROT_SYSTEM = 0;
    public static final int LIFT_VERTICAL_SYSTEM = 1;
    public static final int LIFT_HORIZONTAL_SYSTEM = 2;
    public static final int VISION_X_SYSTEM = 3;
    public static final int VISION_Y_SYSTEM = 4;
    public static final int VISION_ROT_SYSTEM = 5;
    public static final int INTAKE_SYSTEM = 6;

    private int NUM_LOG_ENTRIES = 5;

    public boolean[] firstRun = new boolean[INTAKE_SYSTEM + 1];
    public double[] errorSum = new double[INTAKE_SYSTEM + 1];
    public double[] lastOutput = new double[INTAKE_SYSTEM + 1];
    public double[] lastActual = new double[INTAKE_SYSTEM + 1];
    public double[][] errorLog = new double[INTAKE_SYSTEM][NUM_LOG_ENTRIES];

    public double[] stopArray = new double[4];

    private OI() {
        _driverController = new Joystick(Addresses.CONTROLLER_DRIVER);
        _driverJoystickRight = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_RIGHT);
        _driverJoystickLeft = new Joystick(Addresses.CONTROLLER_DRIVER_STICK_LEFT);
        _operatorController1 = new Joystick(Addresses.CONTROLLER_OPERATOR1);
        _operatorController2 = new Joystick(Addresses.CONTROLLER_OPERATOR2);

        _driverControllerAButton = new JoystickButton(_driverController, 1);
        _driverControllerBButton = new JoystickButton(_driverController, 2);
        _driverControllerXButton = new JoystickButton(_driverController, 3);
        _driverControllerYButton = new JoystickButton(_driverController, 4);

        _operatorCargoPhaseOne = new JoystickButton(_operatorController1, 8); //Change values if needed
        _operatorCargoPhaseTwo = new JoystickButton(_operatorController1, 7); //Change values if needed
        _operatorCargoPhaseThree = new JoystickButton(_operatorController1, 6); //Change values if needed

        _operatorHatchPhaseOne = new JoystickButton(_operatorController2, 8);
        _operatorHatchPhaseTwo = new JoystickButton(_operatorController2, 7);
        _operatorHatchPhaseThree = new JoystickButton(_operatorController2, 6);

        _operatorLiftUpAndDown = new JoystickButton(_operatorController1, 1);
        _operatorIntakeRotate = new JoystickButton(_operatorController2, 2);
        _operatorMastBackAndForth = new JoystickButton(_operatorController2, 1);
        _operatorCargoIn = new JoystickButton(_operatorController1, 2);

        //Cargo Loading Station or Cargo Ship
        _operatorCargoLSOCS = new JoystickButton(_operatorController1, 3);
        _operatorCargoOut = new JoystickButton(_operatorController1, 4);
        _operatorCargoFloor = new JoystickButton(_operatorController1, 5);

        //Hatch Loading Station or Cargo Ship
        _operatorHatchLSOCS = new JoystickButton(_operatorController2, 3);
        _operatorHatchFloor = new JoystickButton(_operatorController2, 5);
        _operatorHatchRelease = new JoystickButton(_operatorController2, 4);

        /*_operatorPhaseOne.whenPressed(new VerticalShift(Variables.getInstance().HATCH_LOW, 1)); //go to phase 1
        _operatorPhaseTwo.whenPressed(new VerticalShift(2, 1)); //go to phase 2
        _operatorPhaseThree.whenPressed(new VerticalShift(3, 1)); //go to phase 3

        _operatorLeftBumper.whenActive(new HorizontalShift(0, 1)); //1 means to go left (or backward) hopefully
        _operatorRightBumper.whenActive(new HorizontalShift(1, -1)); //-1 means to go right (or forward) */
        
        FollowOneTarget followOneTarget;
        _driverControllerYButton.whileActive(followOneTarget = new FollowOneTarget()); //follows when pressed
        
        FollowTwoTarget followTwoTarget;
        _driverControllerXButton.whileActive(followTwoTarget = new FollowTwoTarget());
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

    /**
     * Called in commands to return the joystick axis which is converted into the set speed of the motor
     */
    
    public double getLiftVertical() {
        return -_operatorController2.getRawAxis(1) * 0.5;
    }

    public double getLiftHorizontal() {
        return _operatorController2.getRawAxis(0) * 0.5;
    }

    public boolean isHeadless() {
        if (SmartDashboard.getBoolean("Joysticks Enabled", false)) { 
            return getLeftStickTrigger();
        } else {
            return getRightBumper();
        }
    }
    
    private boolean getRightBumper() {
        return _driverController.getRawButton(6);
    }
    
    public boolean getLeftStickTrigger() {
        return _driverJoystickLeft.getRawButton(1);
    }

    public boolean isForwardOnlyMode() {
        if (SmartDashboard.getBoolean("Joysticks Enabled", false)) {
            return getLeftJoystickForwardOnlyMode();
        } else {
            return getAButton();
        }
    }

    private boolean getAButton() {
        return _driverController.getRawButton(1);
    }

    private boolean getLeftJoystickForwardOnlyMode() {
        return _driverJoystickLeft.getRawButton(2);
    }

    public boolean getBButton() {
        //SmartDashboard.putBoolean("90 Right", _driverController.getRawButton(2));
        return _driverController.getRawButton(2);
    }

    public boolean getTurnLeft90() {
        if (SmartDashboard.getBoolean("Joysticks Enabled", false)) {
            return false;//getTurn90LeftButton();
        } else {
            return false;//getXButton();
        }
    }

    public boolean getTurnRight90() {
        if (SmartDashboard.getBoolean("Joysticks Enabled", false)) {
            return false;//getTurn90RightButton();
        } else {
            return false;//getBButton();
        }
    }

    private boolean getXButton() {
        //SmartDashboard.putBoolean("90 Left", _driverController.getRawButton(3));
        return _driverController.getRawButton(3);
    }

    private boolean getTurn90LeftButton() {
        return _driverJoystickRight.getRawButton(5);
    }

    public boolean getYButton() {
        return _driverController.getRawButton(4);
    }

    private boolean getTurn90RightButton() {
        return _driverJoystickRight.getRawButton(6);
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
            case ROT_SYSTEM:
                SmartDashboard.putNumber("Rot PID Target", target);
                SmartDashboard.putNumber("Rot PID Error", error);
                break;
            case VISION_X_SYSTEM:
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
                break;
            default:
                break;
        }
    }

    public boolean checkIfNeedBeRun(int system, double error) {
        switch (system) {
            case ROT_SYSTEM:
                if (DriveTrain.getInstance().turnInProgress && Math.abs(error) < 3.0) { // note that we actually want a tolerance here
                    DriveTrain.getInstance().turnInProgress = false;
                    return false;
                }
                return true;
            case VISION_ROT_SYSTEM:
                if (DriveTrain.getInstance().turnInProgress && Math.abs(error) < 3.0) {
                    DriveTrain.getInstance().turnInProgress = false;
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
            default:
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
            lastOutput[system] = termP;
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

        lastOutput[system] = output;

        return output;
    }
    
}