package frc.robot.commands.driverassistance;

import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchGroundTwo extends CommandGroup {
    
    public HatchGroundTwo() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.HATCH_ONE, Variables.WRIST_0, Variables.MAST_CURRENT_POS));
    }

}