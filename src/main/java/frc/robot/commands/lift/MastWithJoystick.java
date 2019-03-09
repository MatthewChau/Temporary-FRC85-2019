/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.OI;
import frc.robot.subsystems.Mast;

import edu.wpi.first.wpilibj.command.Command;

public class MastWithJoystick extends Command {
    public MastWithJoystick() {
        requires(Mast.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Mast.getInstance().horizontalShift(OI.getInstance().getOperatorJoystickY());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !OI.getInstance().getOperatorLiftHorizontal();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Mast.getInstance().horizontalShift(0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
