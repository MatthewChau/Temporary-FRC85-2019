/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;

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
    _leftFrontMotor = new WPI_TalonSRX(Addresses.LEFT_FRONT_MOTOR);
    _leftBackMotor = new WPI_TalonSRX(Addresses.LEFT_BACK_MOTOR);
    _rightFrontMotor = new WPI_TalonSRX(Addresses.RIGHT_FRONT_MOTOR);
    _rightBackMotor = new WPI_TalonSRX(Addresses.RIGHT_BACK_MOTOR);
  
    _mDrive = new MecanumDrive(_leftFrontMotor, _leftBackMotor, _rightFrontMotor, _rightBackMotor);
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
    // setDefaultCommand(new MySpecialCommand());
  }
}
