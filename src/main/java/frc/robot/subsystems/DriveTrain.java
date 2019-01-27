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
import frc.robot.sensors.IMU;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private double kP = 0.1, kI = 0.0, kD = 0.0;
    private double currAngle = 0.0;
    private double[] wheelSpeeds = new double[4];

    private static DriveTrain _instance = null;
    private TalonSRX _leftFrontMotor, _leftBackMotor, _rightFrontMotor, _rightBackMotor;

    private MecanumDrive _mDrive;

    private DriveTrain() {
        _leftFrontMotor = new TalonSRX(Addresses.DRIVETRAIN_LEFT_FRONT_MOTOR);
        _leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _leftBackMotor = new TalonSRX(Addresses.DRIVETRAIN_LEFT_BACK_MOTOR);
        _leftBackMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _rightFrontMotor = new TalonSRX(Addresses.DRIVETRAIN_RIGHT_FRONT_MOTOR);
        _rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _rightBackMotor = new TalonSRX(Addresses.DRIVETRAIN_RIGHT_BACK_MOTOR);
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
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveWithJoystick());
    }

    /** 
     * args for inputs: 
     * 0 - bool for headedness
     * 1 - xSpeed
     * 2 - ySpeed
     * 3 - zRotation
     * 4 - gyroAngle (0 if not really using)
     */

    public void cartDrive(double[] inputs) {
        int i;

        if (Math.abs(inputs[1]) > Variables.getInstance().DEADBAND 
            || Math.abs(inputs[2]) > Variables.getInstance().DEADBAND
            || Math.abs(inputs[3]) > Variables.getInstance().DEADBAND) {
            for (i = 0; i < 3; i++) {
                if (inputs[i] > 1.0) {
                    inputs[i] = 1.0;
                } else if (inputs[i] < -1.0) {
                    inputs[i] = -1.0; 
                }
            }

            Vector2d vector = new Vector2d(inputs[2], inputs[1]);
            if (inputs[0] == 1) { // if headless, account for it
                vector.rotate(inputs[4]);
            }

            if (Math.abs(inputs[3]) < Variables.getInstance().DEADBAND || inputs[0] == 0) { // will deal with headless later
                inputs[3] = applyPID(inputs[3], getTargetAngle(vector));
            }

            wheelSpeeds[0] = vector.x + vector.y + inputs[3];
            wheelSpeeds[1] = vector.x - vector.y - inputs[3];
            wheelSpeeds[2] = vector.x - vector.y + inputs[3];
            wheelSpeeds[3] = vector.x + vector.y - inputs[3];

            limitSpeeds(wheelSpeeds); // set anything over 1 to 1 and adjust proportionally

            _leftFrontMotor.set(ControlMode.PercentOutput, wheelSpeeds[0]);
            _rightFrontMotor.set(ControlMode.PercentOutput, -wheelSpeeds[1]);
            _leftBackMotor.set(ControlMode.PercentOutput, wheelSpeeds[2]);
            _rightBackMotor.set(ControlMode.PercentOutput, -wheelSpeeds[3]);
        }
    }

    public void limitSpeeds(double[] speeds) {
        double maxMagnitude = Math.abs(speeds[0]);
        int i;
        for (i = 0; i < 3; i++) {
            if (Math.abs(speeds[i]) > maxMagnitude) {
                maxMagnitude = Math.abs(speeds[i]);
            }
        }
        if (maxMagnitude > 1.0) {
            for (i = 0; i < 3; i++) {
                speeds[i] = speeds[i] / maxMagnitude;
            }
        }
    }

    public double applyPID(double currAngle, double targetAngle) {
        double newRot;
        double error = targetAngle - IMU.getInstance().getFusedHeading();

        newRot = kP * error;

        SmartDashboard.putNumber("Angle Error", newRot);

        return newRot;
    }

    public double getTargetAngle(Vector2d vector) {
        double targetAngle;
        targetAngle = Math.atan(OI.getInstance().getYInput() / OI.getInstance().getXInput());

        return targetAngle;
    }

    /**
     * Returns the selected motor's encoder position (count)
     * 1 Rotation = 4096 counts
     */
    public double getLeftFrontPosition() {
        return _leftFrontMotor.getSelectedSensorPosition();
    }

    public double getLeftBackPosition() {
        return _leftBackMotor.getSelectedSensorPosition();
    }

    public double getRightFrontPosition() {
        return _rightFrontMotor.getSelectedSensorPosition();
    }

    public double getRightBackPosition() {
        return _rightBackMotor.getSelectedSensorPosition();
    }

    /**
     * Returns a value of (-1.0,1.0)
     */
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

    public TalonSRX getIMUTalon() {
        return _leftBackMotor;
    }

}