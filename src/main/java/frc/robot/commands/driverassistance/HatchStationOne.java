package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.intake.ActivateIntake;
import frc.robot.commands.driverassistance.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchStationOne extends CommandGroup {
    
    public HatchStationOne() {
        addSequential(new Interrupt());

        addSequential(new Place(Variables.HATCH_ONE, Variables.WRIST_30));
    }

}

