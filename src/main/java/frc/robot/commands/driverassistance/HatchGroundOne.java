package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.ElevatorPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchGroundOne extends CommandGroup {
    
    public HatchGroundOne() {
        addSequential(new Interrupt());

        //addSequential(new Place(Variables.HATCH_FLOOR, Variables.WRIST_HATCH_FLOOR));
        addSequential(new ElevatorPosition(Variables.HATCH_FLOOR));
        addSequential(new WristPosition(Variables.WRIST_HATCH_FLOOR));
    }

}