/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorPosition extends Command {

    private double _target, _initial;
    private boolean _run = true;

    public ElevatorPosition(double target) {
        requires(Elevator.getInstance());
        _initial = target;
    }

    @Override
    protected void initialize() {
        if (_initial < 0) {
            _target = Elevator.getInstance().getVerticalPosition();
            _run = false;
        } else {
            _target = _initial;
        }
    }

    @Override
    protected void execute() {
        Elevator.getInstance().setTargetPosition(_target);
        Elevator.getInstance().changeAdjustingBool(true);
        Elevator.getInstance().verticalShift(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (!Elevator.getInstance().getAdjustingBool() || !_run);
    }

    @Override
    protected void end() {
        Elevator.getInstance().setElevatorMotors(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}