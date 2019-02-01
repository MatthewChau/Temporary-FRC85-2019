/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.sensors.IMU;
import frc.robot.subsystems.Lift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.lift.HorizontalShift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.subsystems.DriveTrain;
import frc.robot.commands.drivetrain.FollowTarget;
import frc.robot.commands.lift.HorizontalShift;

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

    private JoystickButton _driverLeftBumper, _driverAButton, _driverBButton;
    private JoystickButton _operatorPhaseZero, _operatorPhaseOne, _operatorPhaseTwo, _operatorPhaseThree, _operatorLeftBumper, _operatorRightBumper;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;
    private double _liftSpeed = 0;

    private double _gyroAngle;

    public int ROT_SYSTEM = 0;
    public int LIFT_UPDOWN_SYSTEM = 1;
    public int LIFT_FRONTBACK_SYSTEM = 2;
    public int INTAKE_SYSTEM = 3;

    private boolean[] firstRun = new boolean[INTAKE_SYSTEM];
    private double[] errorSum = new double[INTAKE_SYSTEM];
    private double[] lastOutput = new double[INTAKE_SYSTEM];
    private double[] lastActual = new double[INTAKE_SYSTEM];

    private OI() {
        _driverController = new Joystick(Addresses.CONTROLLER_DRIVER);
        _operatorController = new Joystick(Addresses.CONTROLLER_OPERATOR);

        _operatorPhaseZero = new JoystickButton(_operatorController, 1); //Change values if needed
        _operatorPhaseOne = new JoystickButton(_operatorController, 2); //Change values if needed
        _operatorPhaseTwo = new JoystickButton(_operatorController, 3); //Change values if needed
        _operatorPhaseThree = new JoystickButton(_operatorController, 4); //Change values if needed

        _operatorLeftBumper = new JoystickButton(_operatorController, 1);
        _operatorRightBumper = new JoystickButton(_operatorController, 2);

        _driverAButton = new JoystickButton(_driverController, 4); // Change to A button
        _driverAButton = new JoystickButton(_driverController, 5); // Change to B button

        _operatorPhaseZero.whenPressed(new VerticalShift(0, 1)); //go to phase 0
        _operatorPhaseOne.whenPressed(new VerticalShift(1, 1)); //go to phase 1
        _operatorPhaseTwo.whenPressed(new VerticalShift(2, 1)); //go to phase 2
        _operatorPhaseThree.whenPressed(new VerticalShift(3, 1)); //go to phase 3

        _operatorLeftBumper.whenActive(new HorizontalShift(0, 1)); //1 means to go left (or backward) hopefully
        _operatorRightBumper.whenActive(new HorizontalShift(1, -1)); //-1 means to go right (or forward) hopefully

        //FollowTarget followTarget;
        //_driverAButton.whenPressed(followTarget = new FollowTarget()); //follows when pressed
        //_driverBButton.cancelWhenPressed(followTarget); //cancels following when pressed

        // init the pid stuffs

        Arrays.fill(firstRun, true);
        Arrays.fill(errorSum, 0.0);
        Arrays.fill(lastOutput, 0.0);
        Arrays.fill(lastActual, 0.0);
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
    public double getVerticalLift() {
        if (!Lift.getInstance().checkVerticalLift(_operatorController.getRawAxis(1))) {
            return _liftSpeed = 0;
        } else {
            return _liftSpeed = _operatorController.getRawAxis(1);
        }
    }

    public double getHorizontalLift() {
        if (!Lift.getInstance().checkHorizontalLift(_operatorController.getRawAxis(0))) {
            return _liftSpeed = 0;
        } else {
            return _liftSpeed = _operatorController.getRawAxis(0);
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

    public double fixArcTangent(double angle, double x, double y) { // fix an angle output by arctan, it will always be in the top left otherwise
        if (x > 0.0 && y > 0.0) {
            angle *= -1.0; // multiply the angle by -1 to place it in the correct quadrant
        } else if (x > 0.0 && y < 0.0) {
            angle += 90.0; // subtract 90 to place the angle in the top right, where it should be
        } else if (x < 0.0 && y < 0.0) {
            angle = (180.0 - angle); // completely invert the angle so that it is in the bottom right, where it should be
        }
        // do nothing if x is less than 0 & y is positive
        return angle;
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
        //lastActual[system] = current;

        // because the I term is the area under the curve, it gets a higher weight if it's been going on for a longer time, hence the errorSum
        // formula:  kI * errorSum (sum of all previous errors)
        termI = 0;//kI * errorSum[system];
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

        // figure out a basis to reset the error sum here
        // what mason did last year was just make it so that it had like zero influence while assigning higher precedence to the D term

        errorSum[system] += error;

        lastOutput[system] = output;

        return output;
    }
    
}