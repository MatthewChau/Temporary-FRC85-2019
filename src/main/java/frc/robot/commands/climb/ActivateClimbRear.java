/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbRear;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateClimbRear extends Command {

    private double _speed, _timeout;

    public ActivateClimbRear(double speed) {
        requires(ClimbRear.getInstance());
        _speed = speed;
        _timeout = 0;
    }

    public ActivateClimbRear(double speed, double seconds) {
        requires(ClimbRear.getInstance());
        _speed = speed;
        _timeout = seconds;
    }

    @Override
    protected void initialize() {
       setTimeout(_timeout); 
    }

    @Override
    protected void execute() {
        ClimbRear.getInstance().setClimbRearMotor(_speed);
        //ClimbRear.getInstance().moveClimbRear(_speed);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        ClimbRear.getInstance().setClimbRearMotor(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}