/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.RearClimb;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateRearClimb extends Command {

    private double _speed, _timeout;

    public ActivateRearClimb(double speed, double seconds) {
        requires(RearClimb.getInstance());
        _speed = speed;
        _timeout = seconds;
    }

    @Override
    protected void initialize() {
       setTimeout(_timeout); 
    }

    @Override
    protected void execute() {
        RearClimb.getInstance().setRearClimbMotor(_speed);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}