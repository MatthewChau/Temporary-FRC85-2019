package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.ActivateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoStationOne extends CommandGroup {
    
    public CargoStationOne() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.CARGO_ONE, Variables.WRIST_0));
        addSequential(new ActivateIntake(Variables.ROLLER_IN));
    }
    
}