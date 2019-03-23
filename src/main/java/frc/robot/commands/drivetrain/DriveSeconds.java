/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class DriveSeconds extends Command {

    double _speed, _timeout;
    
    public DriveSeconds(double ySpeed, double timeout) {
        requires(DriveTrain.getInstance());
        _speed = ySpeed;
        _timeout = timeout;
    }

    @Override
    protected void initialize() {
        setTimeout(_timeout);
    }

    @Override
    protected void execute() {
        DriveTrain.getInstance().cartDrive(0, _speed, 0, IMU.getInstance().getFusedHeading());
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        DriveTrain.getInstance().cartDrive(0, 0, 0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
    
}
