/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Addresses;

/**
 * Add your docs here.
 */
public class ProxSensors extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static ProxSensors _instance = null;
  private DigitalInput _topLimit, _botLimit, _limit, _otraLimit;

  private ProxSensors() {
    _topLimit = new DigitalInput(Addresses.LIFT_TOP_LIMIT);
    _botLimit = new DigitalInput(Addresses.LIFT_BOTTOM_LIMIT);

    // Dunno what these are for, but they're on the lift, might be sideways
    _limit = new DigitalInput(Addresses.LIFT_LIMIT);
    _otraLimit = new DigitalInput(Addresses.LIFT_OTRA_LIMIT);

  }

  public static ProxSensors getInstance() {
    if (_instance == null) {
      _instance = new ProxSensors();
    }
    return _instance;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public boolean getTopLimit() {
    return _topLimit.get();
  }

  public boolean getBottomLimit() {
    return _botLimit.get();
  }

  public boolean getLimit() {
    return _limit.get();
  }

  public boolean getOtraLimit() {
    return _otraLimit.get();
  }
  
}
