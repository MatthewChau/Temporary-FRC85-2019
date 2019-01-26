/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class HorizontalShift extends Command {

    private int _direction, _targetPhase;
    private int _tolerance;

    public HorizontalShift(int phase, int direction) {
        requires(Lift.getInstance());
        _direction = direction;
        _targetPhase = phase;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Lift.getInstance().horizontalShift(_direction);
        //Change to "Lift.getInstance().horizontalShift(_direction * OI.getInstance().getRawAxis)" 
        //or something like that if you wish to manipulate based on joystick axis.
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(Lift.getInstance().getCimEncoderValue() - Lift.getInstance().getHorizontalPhase(_targetPhase)) <= _tolerance);
    }

    @Override
    protected void end() {
        super.end();
        Lift.getInstance().horizontalShift(0);
    }
}