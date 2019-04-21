/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class ActivateWrist extends Command {

    private double _speed;

    public ActivateWrist(double speed) {
        requires(Intake.getInstance());
        _speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Intake.getInstance().setWristMotor(_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Sensors.getInstance().getIntakeTopLimit();
    }

    @Override
    protected void interrupted() {
        Intake.getInstance().setWristMotor(0.0);
    }

    @Override
    protected void end() {
        interrupted();
    }

}