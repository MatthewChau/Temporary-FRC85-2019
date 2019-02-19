/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleIntakeSolenoid extends Command {

    public ToggleIntakeSolenoid() {
        requires(Intake.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Intake.getInstance().setIntakeSolenoid(!Intake.getInstance().getIntakeOneSolenoid());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

}