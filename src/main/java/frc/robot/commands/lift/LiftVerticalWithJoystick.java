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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftVerticalWithJoystick extends Command {

    public LiftVerticalWithJoystick(double target) { // pass in 0.0 if you want stuff to work fine
        requires(LiftVertical.getInstance());
        if (target != 0.0) {
            LiftVertical.getInstance().setTargetPosition(target);
            LiftVertical.getInstance().changeAdjustingBool(true);
        }
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (SmartDashboard.getBoolean("Safe?", false)) {
            LiftVertical.getInstance().verticalShift(OI.getInstance().getOperatorJoystick()); // finally run the method for it
        }
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
        LiftVertical.getInstance().stopMotors();
    }

}
