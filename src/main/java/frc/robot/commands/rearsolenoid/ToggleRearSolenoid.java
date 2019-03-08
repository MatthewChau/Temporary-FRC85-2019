/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.rearsolenoid;

import frc.robot.subsystems.RearSolenoid;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleRearSolenoid extends Command {

    public ToggleRearSolenoid() {
        requires(RearSolenoid.getInstance());
    }

    @Override
    protected void initialize() {
        RearSolenoid.getInstance().setRearSolenoid(!RearSolenoid.getInstance().getRearSolenoidOne());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
