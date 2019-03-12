/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateElevator extends Command {

    private double _speed, _timeout;
    
    public ActivateElevator(double speed, double seconds) {
        requires(Elevator.getInstance());
        _speed = speed;
        _timeout = seconds;
    }

    @Override
    protected void initialize() {
        setTimeout(_timeout);
    }

    @Override
    protected void execute() {
        Elevator.getInstance().setElevatorMotors(_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}