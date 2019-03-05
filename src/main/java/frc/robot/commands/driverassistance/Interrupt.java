/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driverassistance;

import frc.robot.subsystems.Interruptable;
import edu.wpi.first.wpilibj.command.Command;

public class Interrupt extends Command {


    public Interrupt() {
        requires(Interruptable.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
       
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

}