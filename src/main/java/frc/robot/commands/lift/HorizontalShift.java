/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.LiftHorizontal;

import edu.wpi.first.wpilibj.command.Command;

public class HorizontalShift extends Command {

    private int _speed, _targetPosition;
    private int _tolerance = 10;

    public HorizontalShift(int position, int speed) {
        requires(LiftHorizontal.getInstance());
        _speed = speed;
        _targetPosition = position;
    }

    @Override
    protected void initialize() {
        super.initialize();
        LiftHorizontal.getInstance().horizontalShift(_targetPosition, _speed);
        //Change to "LiftHorizontal.getInstance().horizontalShift(_direction * OI.getInstance().getRawAxis)" 
        //or something like that if you wish to manipulate based on joystick axis.
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(LiftHorizontal.getInstance().getHorizontalPosition() - _targetPosition) <= _tolerance);
    }

    @Override
    protected void end() {
        super.end();
        LiftHorizontal.getInstance().horizontalShift(0);
    }
}