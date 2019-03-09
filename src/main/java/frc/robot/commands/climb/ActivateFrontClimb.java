/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.FrontClimb;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateFrontClimb extends Command {

    private double _speed, _timeout;

    public ActivateFrontClimb(double speed, double seconds) {
        requires(FrontClimb.getInstance());
        _speed = speed;
        _timeout = seconds;
    }

    @Override
    protected void initialize() {
       setTimeout(_timeout); 
    }

    @Override
    protected void execute() {
        FrontClimb.getInstance().setFrontClimbMotors(_speed);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}