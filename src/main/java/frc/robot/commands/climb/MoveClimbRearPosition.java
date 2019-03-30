/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.Variables;
import frc.robot.subsystems.ClimbRear;

import edu.wpi.first.wpilibj.command.Command;

public class MoveClimbRearPosition extends Command {

    private double _initial, _target;

    public MoveClimbRearPosition(double target) {
        requires(ClimbRear.getInstance());
        _initial = target;
    }

    @Override
    protected void initialize() {
        _target = _initial;
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbUnlocked());
    }

    @Override
    protected void execute() {
        ClimbRear.getInstance().setAdjustingBool(true);
        ClimbRear.getInstance().setTargetPosition(_target);
        ClimbRear.getInstance().moveClimbRear(0.0);
    }

    @Override
    protected boolean isFinished() {
        return !ClimbRear.getInstance().getAdjustingBool();
    }

    @Override
    protected void end() {
        ClimbRear.getInstance().setClimbRearMotor(0.0);
        ClimbRear.getInstance().setAdjustingBool(false);
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbLocked());
    }

    @Override
    protected void interrupted() {
        end();
    }

}