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

import frc.robot.commands.drivetrain.FollowTarget;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.lift.HorizontalShift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.lift.HorizontalShift;
import frc.robot.commands.intake.DriveServo;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import java.util.Arrays;

public class OI {

    private static OI _instance;

    private Joystick _driverController;
    private Joystick _operatorController;

    private JoystickButton _driverLeftBumper, _driverAButton, _driverBButton, _driverXButton;
    private JoystickButton _operatorPhaseZero, _operatorPhaseOne, _operatorPhaseTwo, _operatorPhaseThree, _operatorLeftBumper, _operatorRightBumper;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;
    private double _liftSpeed = 0;

    private double _gyroAngle;

    public int ROT_SYSTEM = 0;
    public int LIFT_VERTICAL_SYSTEM = 1;
    public int LIFT_HORIZONTAL_SYSTEM = 2;
    public int VISION_SYSTEM = 3;
    public int INTAKE_SYSTEM = 4;

    private int NUM_LOG_ENTRIES = 5;

    private boolean[] firstRun = new boolean[INTAKE_SYSTEM];
    private double[] errorSum = new double[INTAKE_SYSTEM];
    private double[] lastOutput = new double[INTAKE_SYSTEM];
    private double[] lastActual = new double[INTAKE_SYSTEM];
    private double[][] errorLog = new double[INTAKE_SYSTEM][NUM_LOG_ENTRIES - 1];

    private OI() {
        _driverController = new Joystick(Addresses.CONTROLLER_DRIVER);
        /*_operatorController = new Joystick(Addresses.CONTROLLER_OPERATOR);

        _operatorPhaseZero = new JoystickButton(_operatorController, 1); //Change values if needed
        _operatorPhaseOne = new JoystickButton(_operatorController, 2); //Change values if needed
        _operatorPhaseTwo = new JoystickButton(_operatorController, 3); //Change values if needed
        _operatorPhaseThree = new JoystickButton(_operatorController, 4); //Change values if needed

        _operatorLeftBumper = new JoystickButton(_operatorController, 1);
        _operatorRightBumper = new JoystickButton(_operatorController, 2);
*/
        _driverAButton = new JoystickButton(_driverController, 4); // Change to Y button
        _driverBButton = new JoystickButton(_driverController, 2); // Change to B button
        _driverXButton = new JoystickButton(_driverController, 3);
/*
        _operatorPhaseZero.whenPressed(new VerticalShift(0, 1)); //go to phase 0
        _operatorPhaseOne.whenPressed(new VerticalShift(1, 1)); //go to phase 1
        _operatorPhaseTwo.whenPressed(new VerticalShift(2, 1)); //go to phase 2
        _operatorPhaseThree.whenPressed(new VerticalShift(3, 1)); //go to phase 3

        _operatorLeftBumper.whenActive(new HorizontalShift(0, 1)); //1 means to go left (or backward) hopefully
        _operatorRightBumper.whenActive(new HorizontalShift(1, -1)); //-1 means to go right (or forward) */

        // init the pid stuffs

        Arrays.fill(firstRun, true);
        Arrays.fill(errorSum, 0.0);
        Arrays.fill(lastOutput, 0.0);
        Arrays.fill(lastActual, 0.0);

        FollowTarget followTarget;
        _driverAButton.whileActive(followTarget = new FollowTarget()); //follows when pressed

        _driverXButton.whenPressed(new DriveServo(0.5));
        _driverXButton.whenReleased(new DriveServo(0.0));
    }

    public static OI getInstance() {
        if (_instance == null) {
            _instance = new OI();
        }
        return _instance;
    }

    public double[] getJoystickInput() {
        if (isHeadless()) {
            headlessDrive();
            // 1 or 0 is passed to identify whether the array is headless or headed drive.
            return new double[] {1, _xSpeed, _ySpeed, _zRotation, _gyroAngle};
        } else {
            headlessDrive();
            return new double[] {0, _xSpeed, _ySpeed, _zRotation, _gyroAngle};
        }
    }

    private void headedDrive() {
        // Deadband is initialized in subsystem DriveTrain with the mecanum drive constructor.
        _xSpeed = -_driverController.getRawAxis(0);
        _ySpeed = _driverController.getRawAxis(1);
        _zRotation = -_driverController.getRawAxis(4);
        //_gyroAngle = 0.0;
    }

    private void headlessDrive() {
        _xSpeed = -_driverController.getRawAxis(0);
        _ySpeed = _driverController.getRawAxis(1);
        _zRotation = -_driverController.getRawAxis(4);
        _gyroAngle = IMU.getInstance().getFusedHeading();
    }

    public double getXInput() {
        return _driverController.getRawAxis(0);
    }

    public double getYInput() {
        return _driverController.getRawAxis(1);
    }

    /**
     * Called in commands to return the joystick axis which is converted into the set speed of the motor
     */
    
    public double getLiftVertical() {
        if (!LiftVertical.getInstance().checkVerticalLift(_operatorController.getRawAxis(1))) {
            return _liftSpeed = 0;
        } else {
            return _liftSpeed = _operatorController.getRawAxis(1) * 0.5;
        }
    }

    public double getLiftHorizontal() {
        if (!LiftHorizontal.getInstance().checkHorizontalLift(_operatorController.getRawAxis(0))) {
            return _liftSpeed = 0;
        } else {
            return _liftSpeed = _operatorController.getRawAxis(0) * 0.5;
        }
    }

    public boolean isHeadless() {
        SmartDashboard.putBoolean("Headless", _driverController.getRawButton(6));
        return _driverController.getRawButton(6);
    }

    public boolean forwardOnly() {
        SmartDashboard.putBoolean("Forward Only", _driverController.getRawButton(1));
        return _driverController.getRawButton(1);
    }

/*    public boolean[] turn90() {
        boolean[] outputs = new boolean[1];
        outputs[0] = _driverController.getRawButton();
        outputs[1] = _driverController.getRawButton();
        SmartDashboard.putBoolean("Left 90", _driverController.getRawButton());
        SmartDashboard.putBoolean("Right 90", _driverController.getRawButton());
        return outputs;
    }*/

    public double fixArcTangent(double angle, double x, double y) { // fix an angle output by arctan
        if (y >= 0) {
            if (x >= 0) {
                angle -= 180;
            } else {
                angle = 180 - angle;
            }
        }
        // if y is above 0, there should be no need to change anything; the range goes from -90 to 90
        return angle;
    }

    public double applyPID(int system, double current, double target, double kP, double kI, double kD) {
        return applyPID(system, current, target, kP, kI, kD, 0.0, 0.0);
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

        SmartDashboard.putNumber("PID Target", target);
        SmartDashboard.putNumber("PID Error", error);

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
        // we can limit this if we find it to be necessary, it can build up

        output = termP + termI + termD;

        if (outputMax != outputMin) { // if we decide to use mins/maxes on outputs, then we can
            if (output > outputMax) {
                output = outputMax;
            } else if (output < outputMin) {
                output = outputMin;
            }
        }

        SmartDashboard.putNumber("PID Output", output);

        logErrorForIntegral(system, error);

        lastOutput[system] = output;

        return output;
    }
    
}