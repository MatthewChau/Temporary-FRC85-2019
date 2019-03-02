/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class DriveSeconds extends Command {

    double _speed;

    /**
     * @param timeout in seconds
     * @param speed list of speed values
     */
    public DriveSeconds(double ySpeed, double timeout) {
        requires(DriveTrain.getInstance());
        _speed = ySpeed;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        DriveTrain.getInstance().cartDrive(0, _speed, 0, 0);
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
    }
    
}
