package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.ActivateWrist;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.lift.MastPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchGroundTwo extends CommandGroup {
    
    public HatchGroundTwo() {
        addSequential(new Interrupt());

        //addSequential(new Place(Variables.HATCH_ONE, Variables.WRIST_0, Variables.MAST_CURRENT_POS));

        addSequential(new ActivateWrist(1.0)); // run up until limit
        addParallel(new MastPosition(Variables.MAST_CURRENT_POS));
        addSequential(new ElevatorPosition(Variables.HATCH_ONE[Variables.getInstance().isPracticeBot()]));
    }

}