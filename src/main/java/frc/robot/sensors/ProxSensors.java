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
    private DigitalInput _liftTopLimit, _liftBotLimit, _liftLeftLimit, _liftRightLimit;

    private ProxSensors() {
        _liftTopLimit = new DigitalInput(Addresses.LIFT_TOP_LIMIT);
        _liftBotLimit = new DigitalInput(Addresses.LIFT_BOTTOM_LIMIT);

        _liftLeftLimit= new DigitalInput(Addresses.LIFT_LEFT_LIMIT);
        _liftRightLimit = new DigitalInput(Addresses.LIFT_RIGHT_LIMIT);
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
        return _liftTopLimit.get();
    }

    public boolean getBottomLimit() {
        return _liftBotLimit.get();
    }

    public boolean getLeftLimit() {
        return _liftLeftLimit.get();
    }

    public boolean getRightlimit() {
        return _liftRightLimit.get();
    }

}