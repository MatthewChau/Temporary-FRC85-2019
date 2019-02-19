/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.subsystems.LiftVertical;

import edu.wpi.first.wpilibj.command.Command;

public class LiftVerticalWithJoystick extends Command {

    double _speed = 0;

    public LiftVerticalWithJoystick() {
        requires(LiftVertical.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        _speed = OI.getInstance().getOperatorJoystick();

        if (_speed > 0) {
            _speed = _speed * 0.30;
        } else if (_speed < 0) {
            _speed = _speed * 0.167;
        }

        LiftVertical.getInstance().verticalShift(_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (!OI.getInstance().getOperatorLiftVertical() || OI.getInstance().getOperatorLiftHorizontal() || OI.getInstance().getOperatorIntakeRotate()) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        LiftVertical.getInstance().verticalShift(0);
    }

}
