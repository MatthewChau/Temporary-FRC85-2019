/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.driverassistance.Interupt;

/**
 * The intake subsystem
 */
public class Interuptable extends Subsystem {

    private static Interuptable _instance = null;

    private Interuptable() {
        
    }

    public static Interuptable getInstance() {
        if (_instance == null) {
            _instance = new Interuptable();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Interupt());
    }
}