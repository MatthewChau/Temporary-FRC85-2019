/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.commands.drivetrain.DriveWithJoystick;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.Vector2d;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveTrain extends Subsystem {

    private static DriveTrain _instance = null;

    private double kMaxOuputRot = 1.0, kMinOuputRot = -1.0;
    public double targetAngle = 0.0;
    public boolean turnInProgress = false; // only used for the 90 degree turns
    private double[] wheelSpeeds = new double[4];

    private TalonSRX _leftFrontMotor, _leftBackMotor, _rightFrontMotor, _rightBackMotor;

    private DriveTrain() {
        _leftFrontMotor = new TalonSRX(Addresses.DRIVETRAIN_LEFT_FRONT_MOTOR);
		_leftFrontMotor.setNeutralMode(NeutralMode.Coast);
        _leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _leftBackMotor = new TalonSRX(Addresses.DRIVETRAIN_LEFT_BACK_MOTOR);
        _leftBackMotor.setNeutralMode(NeutralMode.Coast);
        _leftBackMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _rightFrontMotor = new TalonSRX(Addresses.DRIVETRAIN_RIGHT_FRONT_MOTOR);
        _rightFrontMotor.setNeutralMode(NeutralMode.Coast);
        _rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _rightBackMotor = new TalonSRX(Addresses.DRIVETRAIN_RIGHT_BACK_MOTOR);
        _rightBackMotor.setNeutralMode(NeutralMode.Coast);
        _rightBackMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    public static DriveTrain getInstance() {
        if (_instance == null) {
            _instance = new DriveTrain();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

    /**
     * @param xSpeed : positive is right
     * @param ySpeed : positive is forward
     * @param zRotation : positive is counter-clockwise
     */
    public void cartDrive(double xSpeed, double ySpeed, double zRotation, double gyroAngle) {
        double[] speed = {xSpeed, ySpeed, zRotation, gyroAngle};
        cartDrive(speed);
    }

    /** 
     * args for inputs: 
     * 0 - xSpeed - positive is right
     * 1 - ySpeed - positive is forward
     * 2 - zRotation - positive is counter-clockwise
     * 3 - gyroAngle 
     */
    public void cartDrive(double[] inputs) {
        int i;

        if (OI.getInstance().getLeftJoystickFaceLeft()) {
            setTurn90TargetAngle(true, inputs[3]); // turn left
        } else if (OI.getInstance().getLeftJoystickFaceRight()) {
            setTurn90TargetAngle(false, inputs[3]); // turn right
        } else if (OI.getInstance().getLeftJoystickFaceBottom()) {
            setTurn180TargetAngle(inputs[3]);
        } else if (Math.abs(inputs[0]) > Variables.DEADBAND_DRIVERSTICK
            || Math.abs(inputs[1]) > Variables.DEADBAND_DRIVERSTICK
            || Math.abs(inputs[2]) > Variables.DEADBAND_Z_DRIVERSTICK
            || turnInProgress) {
            for (i = 0; i <= 2; i++) { // normalize axis inputs (just in case)
                if (inputs[i] > 1.0) {
                    inputs[i] = 1.0;
                } else if (inputs[i] < -1.0) {
                    inputs[i] = -1.0; 
                } else if (i < 2 && Math.abs(inputs[i]) > Variables.DEADBAND_DRIVERSTICK) {
                    inputs[i] = 0;
                } else {
                    if (inputs[2] < Variables.DEADBAND_Z_DRIVERSTICK) {
                        inputs[2] = 0;
                    }
                }
            }

            /*if (Math.abs(inputs[2]) > Variables.getInstance().DEADBAND_Z_DRIVERSTICK) { // scale z input
                inputs[2] -= Variables.getInstance().DEADBAND_Z_DRIVERSTICK;
                inputs[2] /= Variables.getInstance().DEADBAND_Z_DRIVERSTICK;
            }*/
            
            /*for (i = 0; i < 2; i++) { // scale everything using a polynomial
                inputs[i] = ((Variables.A_POLYNOMIAL * Math.pow(inputs[i], 3)) + (Variables.B_POLYNOMIAL * Math.pow(inputs[i], 2)) + (Variables.C_POLYNOMIAL * inputs[i]) + (Variables.D_POLYNOMIAL));
            }*/

            if (OI.getInstance().getRightStickTrigger()) {
                inputs[0] = 0.0;
                inputs[2] = 0.0;
            }

            if (OI.getInstance().getLeftStickTrigger()) { // trigger for lowering speed
                inputs[0] *= 0.6;
                inputs[1] *= 0.33;
                inputs[2] *= 0.45;
            }

            Vector2d vector = new Vector2d(-inputs[1], inputs[0]);
            if (OI.getInstance().isHeadless() || OI.getInstance().isForwardOnlyMode()) { // if headless/forward only, account for it
                vector.rotate(inputs[3]);
            }
            
            if (turnInProgress) { // note that this block exists for the sole purpose of overriding things when they are in progress
                inputs[2] = OI.getInstance().applyPID(OI.ROT_SYSTEM, inputs[3], targetAngle, Variables.getInstance().getDriveKP(), Variables.getInstance().getDriveKI(), Variables.getInstance().getDriveKD());
            } else if (Math.abs(inputs[2]) < Variables.DEADBAND_DRIVERSTICK && !OI.getInstance().isHeadless()) { // we will deal with headless later ahaha
                if (OI.getInstance().isForwardOnlyMode()) { // finally we check if we are in that specific mode
                    setForwardOnlyTargetAngle();
                    fixTargetAngle(inputs[3]);
                    inputs[2] = OI.getInstance().applyPID(OI.ROT_SYSTEM, inputs[3], targetAngle, Variables.getInstance().getDriveKP(), Variables.getInstance().getDriveKI(), Variables.getInstance().getDriveKD(), 1.0, -1.0);
                } else { // normal movement
                    setTargetAngleMoving(inputs[3]);
                    inputs[2] = OI.getInstance().applyPID(OI.ROT_SYSTEM, inputs[3], targetAngle, Variables.getInstance().getDriveKP(), Variables.getInstance().getDriveKI(), Variables.getInstance().getDriveKD(), kMaxOuputRot, kMinOuputRot);
                }
            }

            wheelSpeeds[0] = (vector.x + vector.y - inputs[2]);
            wheelSpeeds[1] = (vector.x - vector.y + inputs[2]);
            wheelSpeeds[2] = (vector.x - vector.y - inputs[2]);
            wheelSpeeds[3] = (vector.x + vector.y + inputs[2]);

            limitSpeeds(wheelSpeeds);

            _leftFrontMotor.set(ControlMode.PercentOutput, wheelSpeeds[0]);
            _rightFrontMotor.set(ControlMode.PercentOutput, -wheelSpeeds[1]);
            _leftBackMotor.set(ControlMode.PercentOutput, wheelSpeeds[2]);
            _rightBackMotor.set(ControlMode.PercentOutput, -wheelSpeeds[3]);
        } else {
            _leftFrontMotor.set(ControlMode.PercentOutput, 0);
            _rightFrontMotor.set(ControlMode.PercentOutput, 0);
            _leftBackMotor.set(ControlMode.PercentOutput, 0);
            _rightBackMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    private void setTargetAngleMoving(double gyroAngle) { // if necessary, change the target angle
        if (Math.abs(Math.abs(gyroAngle) - Math.abs(targetAngle)) > Variables.TOLERANCE_ANGLE) {
            targetAngle = gyroAngle;
        }
    }

    /**
     * okay let's do some math explanations & why i thought this was a good idea 
     * the goal is to get the angle with left from the y axis being positive going
     * counter-clockwise (because that's how gyro goes) so we absolute value the
     * whole thing to keep it within range of arctan's use joystick angle formula
     * thus becomes: arctan(-X / |Y|) then we fix it using the method in OI to
     * adjust it so that we can use it (i.e. arctan doesn't always return 0-90!),
     * commented there note that this is if you want the robot to go FORWARD facing
     * this angle we then set it to the closest to the current angle that the robot
     * is, using a temp var so that we can pass a value before editing the targetValue
     */
    private void setForwardOnlyTargetAngle() {
        double joystickAngle;
        
        joystickAngle = Math.toDegrees(Math.atan(-OI.getInstance().getLeftXInputJoystick() / Math.abs(OI.getInstance().getLeftYInputJoystick())));
        joystickAngle = OI.getInstance().fixArcTangent(joystickAngle, OI.getInstance().getLeftXInputJoystick(), OI.getInstance().getLeftYInputJoystick());

        if (Math.abs((Math.abs(targetAngle) % 360) - Math.abs(joystickAngle)) > Variables.TOLERANCE_ANGLE) { // if the new angle differs "significantly"
            targetAngle = joystickAngle;
        }
    }

    private void fixTargetAngle(double gyroAngle) {
        double minDiff = 180.0;
        double newTargetAngle = 0.0;
        int i;

        for (i = -Variables.MAX_TURNS; i < Variables.MAX_TURNS; i++) {
            if (Math.abs(targetAngle + 360 * i - gyroAngle) < minDiff) { // if the new diff is less than the recorded minimum thus far
                minDiff = (targetAngle + 360 * i - gyroAngle);
                newTargetAngle = targetAngle + 360 * i; // note that we can't edit targetAngle directly while the loop is happening
            }
        }

        targetAngle = newTargetAngle;
    }

    public void setTurn90TargetAngle(boolean direction, double gyroAngle) {
        if (!turnInProgress) { // checked here because otherwise it would be checked every single time we call the function
            if (direction) { // turn left
                targetAngle = gyroAngle + 90;
            } else { // turn right
                targetAngle = gyroAngle - 90;
            }
            turnInProgress = true;
        }
    }

    public void setTurn180TargetAngle(double gyroAngle) {
        if (!turnInProgress) {
            if (gyroAngle < 0) {
                targetAngle = gyroAngle + 180;
            } else {
                targetAngle = gyroAngle - 180;
            }
            turnInProgress = true;
        }
    }

    public void setTargetAngle(double angle) { // make sure to call fixAngles afterward
        if (!turnInProgress) {
            targetAngle = angle;
            turnInProgress = true;
        }
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    public void setTurnInProgress(boolean bool) {
        turnInProgress = bool;
    }

    public boolean getTurnInProgress() {
        return turnInProgress;
    }

    private void limitSpeeds(double[] speeds) { // take the highest speed & then adjust everything else proportionally if it is over 1
        double maxMagnitude = Math.abs(speeds[0]); // choose an arbitrary one of them to be the max as an init thing
        int i;

        for (i = 0; i < 3; i++) {
            if (Math.abs(speeds[i]) > maxMagnitude) { // if the i'th speed is larger, change maxMag to be the i'th speed
                maxMagnitude = Math.abs(speeds[i]);
            }
        }

        if (maxMagnitude > 1.0) { // this thus normalizes the speeds proportionally speaking
            for (i = 0; i < 3; i++) {
                speeds[i] = speeds[i] / maxMagnitude;
            }
        }
    }

    public void fixAngles(double gyroAngle) { // adjust target angle to gyro angle
        double minDiff = 180.0;
        double newTargetAngle = 0.0;
        int i;

        for (i = -Variables.MAX_TURNS; i < Variables.MAX_TURNS; i++) {
            if (Math.abs(targetAngle + 360 * i - gyroAngle) < minDiff) {
                minDiff = (targetAngle + 360 * i - gyroAngle);
                newTargetAngle = targetAngle + 360 * i; // note that we can't edit targetAngle directly
            }
        }
        targetAngle = newTargetAngle;
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        if (leftSpeed < Variables.DEADBAND_DRIVERSTICK) {
            leftSpeed = 0.0;
        }
        if (rightSpeed < Variables.DEADBAND_DRIVERSTICK) {
            rightSpeed = 0.0;
        }

        wheelSpeeds[0] = leftSpeed;
        wheelSpeeds[1] = rightSpeed;
        wheelSpeeds[2] = leftSpeed;
        wheelSpeeds[3] = rightSpeed;

        _leftFrontMotor.set(ControlMode.PercentOutput, wheelSpeeds[0]);
        _rightFrontMotor.set(ControlMode.PercentOutput, -wheelSpeeds[1]);
        _leftBackMotor.set(ControlMode.PercentOutput, wheelSpeeds[2]);
        _rightBackMotor.set(ControlMode.PercentOutput, -wheelSpeeds[3]);
    }

    public void fpsDrive(double speed, double turn) {
        if (speed < Variables.DEADBAND_DRIVERSTICK) {
            speed = 0.0;
        }
        if (turn < Variables.DEADBAND_Z_DRIVERSTICK) {
            turn = 0.0;
        }

        wheelSpeeds[0] = speed + turn;
        wheelSpeeds[1] = speed - turn;
        wheelSpeeds[2] = speed + turn;
        wheelSpeeds[3] = speed - turn;

        limitSpeeds(wheelSpeeds);

        _leftFrontMotor.set(ControlMode.PercentOutput, wheelSpeeds[0]);
        _rightFrontMotor.set(ControlMode.PercentOutput, -wheelSpeeds[1]);
        _leftBackMotor.set(ControlMode.PercentOutput, wheelSpeeds[2]);
        _rightBackMotor.set(ControlMode.PercentOutput, -wheelSpeeds[3]);
    }

    // Voltage
    public double getLeftFrontVoltage() {
        return _leftFrontMotor.getMotorOutputVoltage();
    }

    public double getLeftBackVoltage() {
        return _leftBackMotor.getMotorOutputVoltage();
    }

    public double getRightFrontVoltage() {
        return _rightFrontMotor.getMotorOutputVoltage();
    }

    public double getRightBackVoltage() {
        return _rightBackMotor.getMotorOutputVoltage();
    }

    // Current
    public double getLeftFrontCurrent() {
        return _leftFrontMotor.getOutputCurrent();
    }

    public double getLeftBackCurrent() {
        return _leftBackMotor.getOutputCurrent();
    }

    public double getRightFrontCurrent() {
        return _rightFrontMotor.getOutputCurrent();
    }

    public double getRightBackCurrent() {
        return _rightBackMotor.getOutputCurrent();
    }

    /**
     * Returns the selected motor's encoder position (count)
     * 1 Rotation = 4096 counts
     */
    public int getLeftFrontPosition() {
        return _leftFrontMotor.getSelectedSensorPosition();
    }

    public int getLeftBackPosition() {
        return _leftBackMotor.getSelectedSensorPosition();
    }

    public int getRightFrontPosition() {
        return _rightFrontMotor.getSelectedSensorPosition();
    }

    public int getRightBackPosition() {
        return _rightBackMotor.getSelectedSensorPosition();
    }

    // What percent output that motor is set to.
    public double getLeftFrontPercent() {
        return _leftFrontMotor.getMotorOutputPercent();
    }

    public double getLeftBackPercent() {
        return _leftBackMotor.getMotorOutputPercent();
    }

    public double getRightFrontPercent() {
        return _rightFrontMotor.getMotorOutputPercent();
    }

    public double getRightBackPercent() {
        return _rightBackMotor.getMotorOutputPercent();
    }

}