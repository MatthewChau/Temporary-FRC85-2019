package frc.robot.commands.driverassistance;

import frc.robot.commands.belttrain.BeltTrainDrive;
import frc.robot.commands.drivetrain.DriveSeconds;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SendItBro extends CommandGroup {

    public SendItBro() {
        addSequential(new Interrupt());

        addSequential(new Wait(0.3));
        addParallel(new DriveSeconds(-0.5, 1.0));
        addSequential(new BeltTrainDrive(.5, 1.0));
    }
    
}