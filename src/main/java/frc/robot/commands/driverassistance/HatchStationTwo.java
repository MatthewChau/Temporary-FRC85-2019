package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.ElevatorPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchStationTwo extends CommandGroup {

    public HatchStationTwo() {
        addSequential(new Interrupt());

        addSequential(new WristPosition(Variables.WRIST_0));
        addSequential(new ElevatorPosition(Variables.HATCH_ONE[Variables.getInstance().isPracticeBot()] + 500));
    }
    
}