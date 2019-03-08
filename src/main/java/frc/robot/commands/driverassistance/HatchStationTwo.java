package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.ElevatorPosition;

public class HatchStationTwo extends CommandGroup {

    public HatchStationTwo() {
        addSequential(new Interrupt());

        addParallel(new WristPosition(Variables.getInstance().WRIST_0));
        addSequential(new ElevatorPosition(Variables.getInstance().HATCH_ONE + 500));
    }
}