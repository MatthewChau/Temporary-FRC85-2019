/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbFront;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.command.Command;

public class MoveClimbFrontPosition extends Command {

    private double _initial, _target;

    public MoveClimbFrontPosition(double target) {
        requires(ClimbFront.getInstance());
        _initial = target;
    }

    @Override
    protected void initialize() {
        _target = _initial;
        IMU.getInstance().setInitialYPR();
    }

    @Override
    protected void execute() {
        ClimbFront.getInstance().setAdjustingBool(true);
        ClimbFront.getInstance().setTargetPosition(_target);
        ClimbFront.getInstance().moveClimbFront(0.0);
    }

    @Override
    protected boolean isFinished() {
        return !ClimbFront.getInstance().getAdjustingBool();
    }

    @Override
    protected void end() {
        ClimbFront.getInstance().setClimbFrontMotors(0.0);
        ClimbFront.getInstance().setAdjustingBool(false);
    }

    @Override
    protected void interrupted() {
        end();
    }

}