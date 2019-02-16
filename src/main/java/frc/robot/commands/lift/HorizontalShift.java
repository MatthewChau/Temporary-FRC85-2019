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

    private int _speedMax, _targetPosition;
    private int _tolerance = 10;

    public HorizontalShift(int targetPosition, int speedMax) {
        requires(LiftHorizontal.getInstance());
        _targetPosition = targetPosition;
        _speedMax = speedMax;
    }

    /**
     * @param timeout in seconds
     */
    public HorizontalShift(int targetPosition, int speedMax, double timeout) {
        requires(LiftHorizontal.getInstance());
        _targetPosition = targetPosition;
        _speedMax = speedMax;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        LiftHorizontal.getInstance().horizontalShift(_targetPosition, _speedMax);
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(LiftHorizontal.getInstance().getHorizontalPosition() - _targetPosition) <= _tolerance);
    }

    @Override
    protected void end() {
        LiftHorizontal.getInstance().horizontalShift(0);
    }
}