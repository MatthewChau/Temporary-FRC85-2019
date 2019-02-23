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

    public LiftVerticalWithJoystick(double target) {
        requires(LiftVertical.getInstance());
        if (target != 0.0) {
            //LiftVertical.target = Variables.getInstance().something();
        }
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        _speed = OI.getInstance().getOperatorJoystick();

        if (!OI.getInstance().getOperatorLiftVertical()) { // if the button isn't pressed
            _speed = 0.0;
        } else if (_speed > 0) { // if the axis is at all positive
            _speed = 0.5;
        } else if (_speed < 0) { // if the axis is at all negative
            _speed = -0.1;
        } else { // if the joystick reads nothing
            _speed = 0.0;
        }

        LiftVertical.getInstance().verticalShift(_speed); // finally run the method for it
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
