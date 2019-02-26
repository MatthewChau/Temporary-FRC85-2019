/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.driverassistance;

import frc.robot.commands.belttrain.BeltTrainDrive;
import frc.robot.commands.belttrain.SetBeltSolenoid;
import frc.robot.commands.rearsolenoid.SetRearSolenoid;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Climb extends CommandGroup {

    public Climb() {
        addSequential(new SetBeltSolenoid(true));
        addSequential(new BeltTrainDrive(0.5, 3));
        addSequential(new SetRearSolenoid(true));
        addSequential(new Wait(0.25));
        addSequential(new SetBeltSolenoid(false));
        addSequential(new Wait(0.25));
        //addSequential(new DriveStraight());
        addSequential(new Wait(0.10));
        addParallel(new SetRearSolenoid(false));
        addSequential(new BeltTrainDrive(0.5, 3));
        //addSequential(new DriveStraight());
    }

}
