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
import frc.robot.commands.climb.ActivateClimbRearDrive;
import frc.robot.commands.drivetrain.ActivateDriveTrain;
import frc.robot.commands.sensors.WaitForClimbFrontSensors;
import frc.robot.commands.sensors.WaitForClimbRearSensor;
import frc.robot.commands.driverassistance.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Climb extends CommandGroup {
    
    public Climb(double position) {
        addSequential(new Interrupt());
        
        addSequential(new MoveClimbPosition(position));
        addParallel(new ActivateClimbRearDrive(0.25));
        addParallel(new ActivateDriveTrain(0.0, 0.15));
        addSequential(new WaitForClimbFrontSensors());
        addParallel(new ActivateClimbRearDrive(0.0));
        addParallel(new ActivateDriveTrain(0.0));
        addSequential(new MoveClimbFrontPosition(0.0));
        addParallel(new ActivateClimbRearDrive(0.25));
        addParallel(new ActivateDriveTrain(0.0, 0.15));
        addSequential(new WaitForClimbRearSensor());
        addParallel(new ActivateClimbRearDrive(0.1));
        addParallel(new ActivateDriveTrain(0.0, 0.1));
        addSequential(new MoveClimbRearPosition(0.0));
        addSequential(new Wait(0.5));
        addSequential(new ActivateClimbRearDrive(0.0));
        addSequential(new ActivateDriveTrain(0.0));
    }

}
