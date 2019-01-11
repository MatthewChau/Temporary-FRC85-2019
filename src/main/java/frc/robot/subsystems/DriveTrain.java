/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import frc.robot.commands.drivetrain.DriveWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

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
    _leftBackMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_LEFT_BACK_MOTOR);
    _rightFrontMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_RIGHT_FRONT_MOTOR);
    _rightBackMotor = new WPI_TalonSRX(Addresses.DRIVETRAIN_RIGHT_BACK_MOTOR);
  
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
   * Headed Mecanum Drive
   */
  public void mDrive(double xSpeed, double ySpeed, double zRotation) {
    _mDrive.driveCartesian(ySpeed, xSpeed, zRotation);
  }

  /**
   * Headless Mecanum Drive
   */
  public void mDrive(double xSpeed, double ySpeed, double zRotation, double gyroAngle) {
    _mDrive.driveCartesian(ySpeed, xSpeed, zRotation, gyroAngle);
  }

  /**
   * Headed or headless depending on array classification (speed[0] is 0 or 1)
   */
  public void mDrive(double[] speed) {
    if (speed[0] == 0) { //Headless
      _mDrive.driveCartesian(speed[1], speed[2], speed[3], speed[4]);
    } else { // Headed
      _mDrive.driveCartesian(speed[1], speed[2], speed[3]);
    }
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

}