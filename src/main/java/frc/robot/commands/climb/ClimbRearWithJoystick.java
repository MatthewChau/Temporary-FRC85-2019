/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.OI;
import frc.robot.subsystems.ClimbRear;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbRearWithJoystick extends Command {

    private double _speed;
    private double _multiplier;

    public ClimbRearWithJoystick() {
        requires(ClimbRear.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        _speed = OI.getInstance().getOperatorJoystickY();

        if (_speed > 0) {
            _multiplier = 0.6;
        } else {
            _multiplier = 0.9;
        }

        ClimbRear.getInstance().moveClimbRear(_speed * _multiplier);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !OI.getInstance().getClimbRearJoystickButton();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        ClimbRear.getInstance().setClimbRearMotor(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
