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

public class ToggleSpike extends Command {

    public ToggleSpike() {
        requires(Spike.getInstance());
    }

    @Override
    protected void initialize() {
        Spike.getInstance().setRelay(Spike.getInstance().getRelay());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
