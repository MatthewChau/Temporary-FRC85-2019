/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbRearDrive;
import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateClimbRearDrive extends Command {
    private double _speed;
    private int _state;

    public ActivateClimbRearDrive(double speed, int state) {
        requires(ClimbRearDrive.getInstance());
        _speed = speed;
        _state = state;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        ClimbRearDrive.getInstance().setClimbRearDriveMotor(_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        switch (_state) {
            case 0:
                return (Sensors.getInstance().getClimbFrontLeftPhotoeye() && Sensors.getInstance().getClimbFrontRightPhotoeye());
            case 1:
                return Sensors.getInstance().getClimbRearPhotoeye();
            default:
                return true;
        }
    }

    @Override
    protected void interrupted() {
        ClimbRearDrive.getInstance().setClimbRearDriveMotor(0.0);
    }

}