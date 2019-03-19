package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HatchRelease extends CommandGroup {
    
    public HatchRelease() {
        addSequential(new Interrupt());

        //addSequential(new ElevatorPosition(Elevator.getInstance().getTargetPosition() - 1300));
        //addParallel(new MastPosition(Mast.getInstance().getHorizontalPosition() - 5000));
        addSequential(new WristPosition(Variables.WRIST_30));
        //addSequential(new MastPosition(Variables.getInstance().MAST_PROTECTED));
    }

}