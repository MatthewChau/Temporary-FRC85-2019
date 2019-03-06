/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.driverassistance.Interrupt;

/**
 * The intake subsystem
 */
public class Interruptable extends Subsystem {

    private static Interruptable _instance = null;

    private Interruptable() {
        
    }

    public static Interruptable getInstance() {
        if (_instance == null) {
            _instance = new Interruptable();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Interrupt());
    }
}