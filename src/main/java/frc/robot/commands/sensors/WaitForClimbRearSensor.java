/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.sensors;

import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForClimbRearSensor extends Command {

    public WaitForClimbRearSensor() {
        requires(Sensors.getInstance());
    }

    @Override
    protected boolean isFinished() {
        if (Sensors.getInstance().getClimbRearPhotoeye()) {
            return true;
        } else {
            return false;
        }
    }

}
