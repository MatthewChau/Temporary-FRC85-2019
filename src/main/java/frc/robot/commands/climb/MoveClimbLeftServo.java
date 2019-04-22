/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbFront;

import edu.wpi.first.wpilibj.command.Command;

public class MoveClimbLeftServo extends Command {

    private double _initial, _target;

    public MoveClimbLeftServo(double angle) {
        requires(ClimbFront.getInstance());
        _initial = angle;
    }

    @Override
    protected void initialize() {
        _target = _initial;
        ClimbFront.getInstance().setServoLeft(_target);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}