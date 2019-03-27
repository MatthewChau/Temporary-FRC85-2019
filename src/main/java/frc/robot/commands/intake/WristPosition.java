/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;
import frc.robot.subsystems.Intake;
import frc.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

public class WristPosition extends Command {
    
    private int _target, _initial;
    private boolean _run = true;
    
    public WristPosition(int target) {
        requires(Intake.getInstance());
        _initial = target;
    }

    @Override
    protected void initialize() {
        if (_initial > 0) {
            _target = Intake.getInstance().getWristPosition();
            _run = false;
        } else {
            _target = _initial;
        }
    }

    @Override
    protected void execute() {
        Intake.getInstance().setTargetPos(_target);
        Intake.getInstance().changeAdjustingBool(true);
        Intake.getInstance().setWrist(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (!Intake.getInstance().getAdjustingBool() || !_run || OI.getInstance().getWristJoystickButton());
    }

    @Override
    protected void end() {
        Intake.getInstance().setWristMotor(0.0);
        Intake.getInstance().changeAdjustingBool(false);
        Intake.getInstance().setTargetPos(Intake.getInstance().getWristPosition());
    }

    @Override
    protected void interrupted() {
        end();
    }

}