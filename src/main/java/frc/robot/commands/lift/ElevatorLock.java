/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.Variables;
import frc.robot.sensors.Sensors;
import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This system of using a clutch to stop the elevator from moving exploits the FRC system of command based.
 * Each subsystem (this is a command, whose subsystem is the elevator) maybe only have one command activate a time, calling a new command interrupts the old command.
 * By setting this as the default command of elevator, when there is no other command sceduled this command will be called.
 * When the command is called, it initalizes and activates the locking mechanism.
 * When the command is interrupted (a command to move the lift), interrupted() runs, unlocking the lift.
 * When the movement command finishes, this command is called again (since it is the default command).
 */
public class ElevatorLock extends Command {

    double _timeout;

    public ElevatorLock() {
        requires(Elevator.getInstance());
        _timeout = 0;
    }

    public ElevatorLock(double timeout) {
        requires(Elevator.getInstance());
        _timeout = timeout;
        setTimeout(_timeout);
    }

    @Override
    protected void initialize() {
        Elevator.getInstance().setServo(Variables.getInstance().getElevatorLocked());
        Elevator.getInstance().setTargetPosition(Elevator.getInstance().getVerticalPosition());
    }

    @Override
    protected void execute() {
        if (_timeout != 0.0) { // if the timeout is not zero then run the pid
            Elevator.getInstance().verticalShift(0.0);
        } else {
            Elevator.getInstance().setElevatorMotors(0.0);
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override 
    protected void end() {
        Elevator.getInstance().setElevatorMotors(0.0);
    }

    @Override
    protected void interrupted() {
        Elevator.getInstance().setServo(Variables.getInstance().getElevatorUnlocked());
        Elevator.getInstance().setTargetPosition(Elevator.getInstance().getVerticalPosition());
    }

}
