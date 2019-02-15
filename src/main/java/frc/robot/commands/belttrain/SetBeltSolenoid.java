/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.belttrain;

import frc.robot.subsystems.BeltSolenoid;
import frc.robot.subsystems.BeltTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SetBeltSolenoid extends Command {

private boolean _extend;



public SetBeltSolenoid (boolean extend) {
	requires(BeltSolenoid.getInstance());
	_extend = extend;
}

	// Use requires() here to declare subsystem dependencies	
	// Called just before this Command runs the first time
	@Override
  	protected void initialize() {
	BeltSolenoid.getInstance().setBeltSolenoid(_extend);
	}

  	// Called repeatedly when this Command is scheduled to run
  	@Override
  	protected void execute() {
		
	}

  	// Make this return true when this Command no longer needs to run execute()
  	@Override
  	protected boolean isFinished() {
    	return false;
  
	}

  	// Called once after isFinished returns true
  	@Override
  	protected void end() {
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
  
	}
}
