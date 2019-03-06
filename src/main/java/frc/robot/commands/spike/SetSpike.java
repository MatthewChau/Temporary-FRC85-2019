/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.spike;

import frc.robot.subsystems.Spike;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;

public class SetSpike extends Command {

    private Value value;

    private boolean _activated;

    public SetSpike(boolean activated) {
        requires(Spike.getInstance());
        _activated = activated;
    }

    @Override
    protected void initialize() {
        if (_activated) {
            value = Value.kOn;
        } else {
            value = Value.kOff;
        }

        Spike.getInstance().setRelay(value);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
