/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
   
import frc.robot.Addresses;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class Spike extends Subsystem {
    
    public static Spike _instance = null;

    private Relay _relay;

    public Spike() {
        _relay = new Relay(Addresses.LIGHT_RELAY);
    }

    public static Spike getInstance() {
        if (_instance == null) {
            _instance = new Spike();
        }
        return _instance;
    }

    @Override
	protected void initDefaultCommand() {
    }

    /**
     * @param value paramter as an enumeration
     * input kOff or kOn
     */
    public void setRelay(boolean bool) {
        if (bool) {
            _relay.set(Value.kForward);
        } else {
            _relay.set(Value.kOff);
        }
    }

    public Value getRelay() {
        return _relay.get();
    }
    
}

  

