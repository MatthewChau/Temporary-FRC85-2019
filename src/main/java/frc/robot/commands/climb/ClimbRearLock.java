/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.Variables;
import frc.robot.sensors.Sensors;
import frc.robot.subsystems.ClimbRear;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbRearLock extends Command {

    double _timeout;

    public ClimbRearLock() {
        requires(ClimbRear.getInstance());
        _timeout = 0;
    }

    public ClimbRearLock(double timeout) {
        requires(ClimbRear.getInstance());
        _timeout = timeout;
        setTimeout(_timeout);
    }

    @Override
    protected void initialize() {
        ClimbRear.getInstance().setClimbRearDriveMotor(0.0);
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbLocked());
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override 
    protected void end() {
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbUnlocked());
        ClimbRear.getInstance().setClimbRearDriveMotor(0.0);
    }

    @Override
    protected void interrupted() {
        ClimbRear.getInstance().setServo(Variables.getInstance().getClimbUnlocked());
        //ClimbRear.getInstance().setClimbRearDriveMotor(0.0);
    }

}
