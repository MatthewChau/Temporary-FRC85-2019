/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbFront;
import frc.robot.subsystems.ClimbRear;
import frc.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

public class MoveClimbPosition extends Command {

    private double _initial, _target;

    public MoveClimbPosition(double target) {
        requires(ClimbRear.getInstance());
        requires(ClimbFront.getInstance());
        _initial = target;
    }

    @Override
    protected void initialize() {
        _target = _initial;
    }

    @Override
    protected void execute() {
        ClimbRear.getInstance().setBothAdjustingBool(true);
        ClimbRear.getInstance().setTargetPosition(_target);
        ClimbRear.getInstance().moveClimbRear(0.0);
        ClimbFront.getInstance().moveClimbFront(0.0);
    }

    @Override
    protected boolean isFinished() {
        return (!ClimbRear.getInstance().getBothAdjustingBool() || !OI.getInstance().getOperatorClimbAuto());
    }

    @Override
    protected void end() {
        ClimbFront.getInstance().setClimbFrontMotors(0.0);
        ClimbRear.getInstance().setClimbRearMotor(0.0);
        ClimbRear.getInstance().setBothAdjustingBool(false);
    }

    @Override
    protected void interrupted() {
        end();
    }

}