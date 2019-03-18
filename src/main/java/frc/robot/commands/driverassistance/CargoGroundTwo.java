package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.ActivateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoGroundTwo extends CommandGroup {
    
    public CargoGroundTwo() {
        addSequential(new Interrupt());

        addSequential(new ActivateIntake(0.0));
        addSequential(new Place(Variables.CARGO_ONE, Variables.WRIST_CARGO, Variables.MAST_CURRENT_POS));
    }
    
}