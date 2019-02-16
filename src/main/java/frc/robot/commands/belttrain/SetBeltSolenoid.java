/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.belttrain;

import frc.robot.subsystems.BeltSolenoid;

import edu.wpi.first.wpilibj.command.Command;

public class SetBeltSolenoid extends Command {

    private boolean _activated;

    public SetBeltSolenoid (boolean activated) {
        requires(BeltSolenoid.getInstance());
        _activated = activated;
    }

    @Override
    protected void initialize() {
        BeltSolenoid.getInstance().setBeltSolenoid(_activated);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
