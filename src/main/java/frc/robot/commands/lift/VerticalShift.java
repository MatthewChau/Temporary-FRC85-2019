/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class VerticalShift extends Command {

    private int _speed, _targetPhase; //positive means going up, negative means going down
    private double _liftTolerance = 10; //the motors have to be within 10 encoder ticks in order for the command to stop

    public VerticalShift(int phase, int speed) {
        requires(Lift.getInstance());
        _targetPhase = phase;
        _speed = speed;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Lift.getInstance().verticalShift(_speed);
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(Lift.getInstance().getLeftEncoderValue() - Lift.getInstance().getPhaseValue(_targetPhase)) <= _liftTolerance);
    }

    @Override
    protected void end() {
        super.end();
        Lift.getInstance().verticalShift(0);
    }

}