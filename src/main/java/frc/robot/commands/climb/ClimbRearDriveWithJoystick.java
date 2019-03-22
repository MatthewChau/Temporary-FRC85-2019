/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.subsystems.ClimbRear;
import frc.robot.subsystems.ClimbRearDrive;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbRearDriveWithJoystick extends Command {

    private double _speed, _position;

    public ClimbRearDriveWithJoystick() {
        requires(ClimbRearDrive.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        _speed = OI.getInstance().getRightYInputJoystick();
        _position = ClimbRear.getInstance().getClimbRearPosition();

        //if (_position < Variables.CLIMB_REAR_SLOW_DOWN_MIN) {
        //    _speed = 0;
        //}

        ClimbRearDrive.getInstance().setClimbRearDriveMotor(_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        ClimbRearDrive.getInstance().setClimbRearDriveMotor(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
