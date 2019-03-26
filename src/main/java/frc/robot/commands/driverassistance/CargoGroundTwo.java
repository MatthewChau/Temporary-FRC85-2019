package frc.robot.commands.driverassistance;

import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.Variables;
import frc.robot.commands.intake.ActivateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoGroundTwo extends CommandGroup {
    
    public CargoGroundTwo() {
        addSequential(new Interrupt());
        
        addSequential(new ActivateIntake(0.0));
        addParallel(new WristPosition(Variables.WRIST_CARGO)); // move the wrist
        addParallel(new MastPosition(Variables.MAST_FORWARD_FOR_CARGO)); // move the mast out to the pos
        addSequential(new ElevatorPosition(Variables.CARGO_ONE)); // move the elevator
    }
    
}