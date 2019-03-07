/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.belttrain;

import frc.robot.OI;
import frc.robot.subsystems.BeltTrain;;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BeltTrainWithJoystick extends Command {
    public BeltTrainWithJoystick() {
        requires(BeltTrain.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        BeltTrain.getInstance().DriveBelt(-OI.getInstance().getBeltInputJoystick());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false; 
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        BeltTrain.getInstance().DriveBelt(0.0);
    }

}
