/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import frc.robot.commands.drivetrain.DriveWithJoystick;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.Vector2d;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private static DriveTrain _instance = null;
	private WPI_TalonSRX _leftFrontMotor, _leftBackMotor, _rightFrontMotor, _rightBackMotor;

    private MecanumDrive _mDrive;

    private DriveTrain() {
        _leftFrontMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_LEFT_FRONT_MOTOR);
        _leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _leftBackMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_LEFT_BACK_MOTOR);
        _leftBackMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _rightFrontMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_RIGHT_FRONT_MOTOR);
        _rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _rightBackMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_RIGHT_BACK_MOTOR);
        _rightBackMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        // Mecanum Drive constructor
        _mDrive = new MecanumDrive(_leftFrontMotor, _leftBackMotor, _rightFrontMotor, _rightBackMotor);
        _mDrive.setDeadband(.1);
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
        /*if (inputs[1] <= Variables.getInstance().DEADBAND 
            && inputs[2] <= Variables.getInstance().DEADBAND 
            && inputs[3] <= Variables.getInstance().DEADBAND) {
            _leftFrontMotor.set(0);
            _rightFrontMotor.set(0);
            _leftBackMotor.set(0);
            _rightBackMotor.set(0);
        } else */if (Math.abs(inputs[1]) > Variables.getInstance().DEADBAND 
            || Math.abs(inputs[2]) > Variables.getInstance().DEADBAND
            || Math.abs(inputs[3]) > Variables.getInstance().DEADBAND) {
            if (inputs[1] > 1.0) {
                inputs[1] = 1.0;
            } else if (inputs[1] < -1.0) {
                inputs[1] = -1.0;
            }
            if (inputs[2] > 1.0) {
                inputs[2] = 1.0;
            } else if (inputs[2] < -1.0) {
                inputs[2] = -1.0;
            }
            Vector2d vector = new Vector2d(inputs[2], inputs[1]);
            if (inputs[0] == 1) { // if headless
                vector.rotate(-inputs[4]);
            }

            double[] wheelSpeeds = new double[4];
            wheelSpeeds[0] = vector.x + vector.y + inputs[3];
            wheelSpeeds[1] = vector.x - vector.y - inputs[3];
            wheelSpeeds[2] = vector.x - vector.y + inputs[3];
            wheelSpeeds[3] = vector.x + vector.y - inputs[3];

            limitSpeeds(wheelSpeeds); // set anything over 1 to 1 and adjust proportionally

            _leftFrontMotor.set(wheelSpeeds[0]);
            _rightFrontMotor.set(-wheelSpeeds[1]);
            _leftBackMotor.set(wheelSpeeds[2]);
            _rightBackMotor.set(-wheelSpeeds[3]);
        }
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
        return _leftFrontMotor.get();
    }

    public double getLeftBackPercent() {
        return _leftBackMotor.get();
    }

    public double getRightFrontPercent() {
        return _rightFrontMotor.get();
    }

    public double getRightBackPercent() {
        return _rightBackMotor.get();
    }

    public WPI_TalonSRX getIMUTalon() {
        return _leftBackMotor;
    }

    public void limitSpeeds(double[] speeds) {
        double maxMagnitude = Math.abs(speeds[0]);
        int i;
        for (i = 0; i < 3; i++) {
            if (Math.abs(speeds[i]) > maxMagnitude) {
                maxMagnitude = speeds[i];
            }
        }
        if (maxMagnitude > 1.0) {
            for (i = 0; i < 3; i++) {
                speeds[i] = speeds[i] / maxMagnitude;
            }
        }
    }

}