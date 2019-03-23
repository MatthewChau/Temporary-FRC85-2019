package frc.robot.commands.driverassistance;

import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ZeroSystems extends CommandGroup {

    /**
     * @param elevatorPosition in encoder counts
     * @param wristPosition in encoder counts
     * @param mastPosition in encoder counts
     */
    public ZeroSystems() {
        addSequential(new Interrupt());

        addSequential(new WristPosition(0));
        addParallel(new MastPosition(0)); // move the mast out to the pos
        addSequential(new ElevatorPosition(0)); // move the elevator
    }

}