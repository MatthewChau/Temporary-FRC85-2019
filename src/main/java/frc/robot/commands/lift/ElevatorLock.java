/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.Variables;
import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorLock extends Command {
    public ElevatorLock() {
        requires(Elevator.getInstance());
    }

    @Override
    protected void initialize() {
        Elevator.getInstance().setServo(Variables.getInstance().getElevatorLocked());
        Elevator.getInstance().setTargetPosition(Elevator.getInstance().getVerticalPosition());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Elevator.getInstance().setServo(Variables.getInstance().getElevatorUnlocked());
        Elevator.getInstance().setTargetPosition(Elevator.getInstance().getVerticalPosition());
    }
}
