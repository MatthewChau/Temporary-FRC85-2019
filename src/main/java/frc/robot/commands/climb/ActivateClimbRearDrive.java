/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbRear;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateClimbRearDrive extends Command {

    private double _speed, _timeout;

    public ActivateClimbRearDrive(double speed) {
        requires(ClimbRear.getInstance());
        _speed = speed;
    }

    public ActivateClimbRearDrive(double speed, double seconds) {
        requires(ClimbRear.getInstance());
        _speed = speed;
    }

    @Override
    protected void initialize() {
        ClimbRear.getInstance().setClimbRearDriveMotor(_speed);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        ClimbRear.getInstance().setClimbRearDriveMotor(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}