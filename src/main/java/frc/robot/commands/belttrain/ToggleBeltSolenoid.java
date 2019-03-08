/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.belttrain;

import frc.robot.subsystems.BeltSolenoid;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleBeltSolenoid extends Command {

    public ToggleBeltSolenoid() {
        requires(BeltSolenoid.getInstance());
    }

    @Override
    protected void initialize() {
        BeltSolenoid.getInstance().setBeltSolenoid(!BeltSolenoid.getInstance().getBeltSolenoid());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
