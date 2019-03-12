package frc.robot.commands.driverassistance;

import frc.robot.commands.driverassistance.Interrupt;
import frc.robot.commands.intake.ActivateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoStationTwo extends CommandGroup {
    
    public CargoStationTwo() {
        addSequential(new Interrupt());
        
        addSequential(new ActivateIntake(0));
    }

}