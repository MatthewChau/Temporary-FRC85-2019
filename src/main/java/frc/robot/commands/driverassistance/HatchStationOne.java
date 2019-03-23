package frc.robot.commands.driverassistance;

import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchStationOne extends CommandGroup {
    
    public HatchStationOne() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.HATCH_ONE, Variables.WRIST_30, Variables.MAST_FORWARD_FOR_HATCH));
    }

}

