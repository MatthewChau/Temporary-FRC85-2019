package frc.robot.commands;

import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.belttrain.BeltTrainDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SendItBro extends CommandGroup {

    double _speed[] = {0.5, 0, 0, 0};

    // i guess this is what we are doing for future reference

    public SendItBro() {
        addSequential(new Wait(1.0));
        addSequential(new DriveSeconds(_speed, 2.0));
        addParallel(new BeltTrainDrive(.5, 2.0));
    }
    
}