/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.rearsolenoid;

import frc.robot.subsystems.RearSolenoid;

import edu.wpi.first.wpilibj.command.Command;

public class SetRearSolenoid extends Command {

    private boolean _activated;

    public SetRearSolenoid(boolean activated) {
        requires(RearSolenoid.getInstance());
        _activated = activated;
    }

    @Override
    protected void initialize() {
        RearSolenoid.getInstance().setRearSolenoid(_activated);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
