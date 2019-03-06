/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.Command;

public class WaitButton extends Command {

    boolean _boolean;

    /**
     * @param button pass in a getButton, command ends when button == true 
     * reverse button return boolean when calling this command.
     */
    public WaitButton(boolean button) {
        _boolean = button;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return _boolean;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
    
}
