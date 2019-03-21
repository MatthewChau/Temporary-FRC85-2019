/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.subsystems.ClimbRear;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateClimbRear extends Command {

    private double _speed;

    public ActivateClimbRear(double speed) {
        requires(ClimbRear.getInstance());
        _speed = speed;
    }

    @Override
    protected void initialize() {
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbUnlocked());
    }

    @Override
    protected void execute() {
        //ClimbRear.getInstance().setClimbRearMotor(_speed);
        ClimbRear.getInstance().moveClimbRear(_speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        ClimbRear.getInstance().setClimbRearMotor(0.0);
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbLocked());
    }

    @Override
    protected void interrupted() {
        end();
    }
}