package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.ElevatorPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchGroundTwo extends CommandGroup {
    
    public HatchGroundTwo() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.HATCH_ONE, Variables.WRIST_0, Variables.MAST_FORWARD_POS));
/*
        addSequential(new WristPosition(0));
        addSequential(new ElevatorPosition(Variables.HATCH_ONE));
*/
    }

}