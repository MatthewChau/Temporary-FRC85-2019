/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.sensors;

import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForClimbFrontSensors extends Command {

    public WaitForClimbFrontSensors() {
        requires(Sensors.getInstance());
    }

    @Override
    protected boolean isFinished() {
        if (Sensors.getInstance().getClimbFrontLeftPhotoeye() && Sensors.getInstance().getClimbFrontRightPhotoeye()) {
            return true;
        } else {
            return false;
        }
    }

}
