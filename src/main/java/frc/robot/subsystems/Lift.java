/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import frc.robot.sensors.ProxSensors;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class Lift extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static Lift _instance = null;
  private TalonSRX _motor;
  private double _speed;

  private Lift() {
    _motor = new TalonSRX(Addresses.LIFT_MOTOR);

  }

  public static Lift getInstance() {
    if (_instance == null) {
      _instance = new Lift();
    }
    return _instance;
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(LiftWithJoystick or something);
  }

  public double getMotorPercent() {
    return _motor.getMotorOutputPercent();
  }

  public void setMotorSpeed(double speed) {
    _motor.set(ControlMode.PercentOutput, speed);
    _speed = speed;
  }

  /**
   * checks limits,
   * then false = don't drive;
   */
  public boolean checkLift() {
    if (ProxSensors.getInstance().getTopLimit() && _speed > 0) {
      return false;
    } else if (ProxSensors.getInstance().getBottomLimit() && _speed < 0) {
      return false;
    } else {
      return true;
    }
  }


}
