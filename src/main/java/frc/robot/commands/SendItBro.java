package frc.robot.commands;

import frc.robot.commands.belttrain.BeltTrainDrive;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.lift.HorizontalShift;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SendItBro extends CommandGroup {

    double _speed[] = {0.0, -0.5, 0.0, 0.0};

    // i guess this is what we are doing for future reference

    public SendItBro() {
        addSequential(new Wait(0.3));
        //addSequential(new HorizontalShift(0, 0.2)); // values subject to change
        addParallel(new DriveSeconds(_speed, 5.0));
        addSequential(new BeltTrainDrive(.5, 5.0));
    }
    
}