/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbFront;

import edu.wpi.first.wpilibj.command.Command;

public class MoveClimbServos extends Command {

    private double _initialL, _initialR, _targetL, _targetR;

    public MoveClimbServos(double leftAngle, double rightAngle) {
        requires(ClimbFront.getInstance());
        _initialL = leftAngle;
        _initialR = rightAngle;
    }

    @Override
    protected void initialize() {
        _targetL = _initialL;
        _targetR = _initialR;
        ClimbFront.getInstance().setServoLeft(_targetL);
        ClimbFront.getInstance().setServoRight(_targetR);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}