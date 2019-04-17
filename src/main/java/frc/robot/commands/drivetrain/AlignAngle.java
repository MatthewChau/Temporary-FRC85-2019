/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import frc.robot.Variables;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class AlignAngle extends Command {

    double _angle, _timeout;
    
    public AlignAngle(double angle, double timeout) {
        requires(DriveTrain.getInstance());
        _angle = angle;
        _timeout = timeout;
    }

    @Override
    protected void initialize() {
        setTimeout(_timeout);
        DriveTrain.getInstance().setTurnInProgress(true);
    }

    @Override
    protected void execute() {
        DriveTrain.getInstance().cartDrive(0, 0, 0, IMU.getInstance().getFusedHeading());
    }

    @Override
    protected boolean isFinished() {
        return (isTimedOut() || !DriveTrain.getInstance().getTurnInProgress());
    }

    @Override
    protected void end() {
        DriveTrain.getInstance().setTurnInProgress(false);
        DriveTrain.getInstance().cartDrive(0, 0, 0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
    
}
