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

public class ActivateDriveTrain extends Command {

    private double _xSpeed, _ySpeed, _zRotation;
    public ActivateDriveTrain(double xSpeed) {
        requires(DriveTrain.getInstance());
        _xSpeed = xSpeed;
        _ySpeed = 0.0;
        _zRotation = 0.0;
    }

    public ActivateDriveTrain(double xSpeed, double ySpeed) {
        requires(DriveTrain.getInstance());           
        _xSpeed = xSpeed;
        _ySpeed = ySpeed;
        _zRotation = 0.0;
    }

    public ActivateDriveTrain(double xSpeed, double ySpeed, double zRotation) {
        requires(DriveTrain.getInstance());
        _xSpeed = xSpeed;
        _ySpeed = ySpeed;
        _zRotation = zRotation;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        DriveTrain.getInstance().cartDrive(_xSpeed, _ySpeed, _zRotation, IMU.getInstance().getFusedHeading());
    }

    @Override
    protected boolean isFinished() {
        return false;
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
