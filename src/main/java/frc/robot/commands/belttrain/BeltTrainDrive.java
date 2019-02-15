/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.belttrain;

import frc.robot.subsystems.BeltTrain;

import edu.wpi.first.wpilibj.command.Command;

public class BeltTrainDrive extends Command {

    double _speed;

    public BeltTrainDrive(double speed) {
        requires(BeltTrain.getInstance());
        _speed = speed;
    }

    public BeltTrainDrive(double speed, double timeout) {
        requires(BeltTrain.getInstance());
        setTimeout(timeout);
        _speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
      protected void initialize() {
  
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        BeltTrain.getInstance().DriveBelt(_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        BeltTrain.getInstance().DriveBelt(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
    
}
