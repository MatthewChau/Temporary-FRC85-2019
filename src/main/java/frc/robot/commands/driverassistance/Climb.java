/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driverassistance;

import frc.robot.commands.climb.MoveClimbPosition;
import frc.robot.commands.climb.MoveClimbFrontPosition;
import frc.robot.commands.climb.MoveClimbRearPosition;
import frc.robot.Variables;
import frc.robot.commands.climb.ActivateClimbRearDrive;
import frc.robot.commands.drivetrain.ActivateDriveTrain;
import frc.robot.commands.driverassistance.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Climb extends CommandGroup {
    
    public Climb(double position) {
        addSequential(new Interrupt());
        
        addSequential(new MoveClimbPosition(position));                                     // actually climb
        addSequential(new Wait(0.1));
        addSequential(new ActivateClimbRearDrive(0.25, 0));                                 // activate climbreardrive & check for front photoeye
        addSequential(new Wait(0.1));
        addSequential(new MoveClimbFrontPosition(0.0));                                     // retract front
        addSequential(new Wait(0.1));
        addSequential(new ActivateClimbRearDrive(0.25, 1));                                 // activate climbreardrive & check for rear photoeye
        addSequential(new Wait(0.1));
        addSequential(new ActivateClimbRearDrive(0.1, 2));                                  // move climbrearwheel forward without checking for a photoeye
        addParallel(new ActivateDriveTrain(0.0, 0.30, true));                               // move drive train forward
        addSequential(new MoveClimbRearPosition(Variables.CLIMB_REAR_SLOW_DOWN_MIN));       // retract rear
        addSequential(new ActivateClimbRearDrive(0, 2), 0.2);                               // stop
    }

}
