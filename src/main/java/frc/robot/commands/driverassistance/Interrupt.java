/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driverassistance;

import frc.robot.subsystems.Interruptable;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command/subsystem works by adding a sequential new interrupt command new a command group.
 * A command group requires all the subsystems which have commands in the command group, even though there is no requires() method.
 * Also, a subsystem can only have one command requiring it active at a time.
 * By calling interrupt, any command group that requires the interruptable subsytem will stop (be interrupted), because it requires all of the related subsystems.
 */
public class Interrupt extends Command {
    public Interrupt() {
        requires(Interruptable.getInstance());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}