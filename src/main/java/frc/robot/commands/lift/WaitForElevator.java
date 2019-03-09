/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForElevator extends Command {

    public WaitForElevator(double timeout) {
        requires(Elevator.getInstance());
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        Elevator.getInstance().setTargetPosition(Elevator.getInstance().getVerticalPosition());
    }

    @Override
    protected void execute() {
        Elevator.getInstance().verticalShift(0.0); // run the pid
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void interrupted() {
    }

}