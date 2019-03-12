package frc.robot.commands.driverassistance;

import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchGroundOne extends CommandGroup {
    
    public HatchGroundOne() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.HATCH_FLOOR, Variables.WRIST_HATCH_FLOOR));
    }

}