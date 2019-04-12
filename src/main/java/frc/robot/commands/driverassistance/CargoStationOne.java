package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.ActivateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoStationOne extends CommandGroup {
    
    public CargoStationOne() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.CARGO_STATION[Variables.getInstance().isPracticeBot()], Variables.WRIST_90 /*?*/, Variables.MAST_FORWARD_FOR_CARGO));
        addSequential(new ActivateIntake(Variables.ROLLER_IN));
    }
    
}