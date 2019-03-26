package frc.robot.commands.driverassistance;

import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Place extends CommandGroup {

    /**
     * @param elevatorPosition in encoder counts
     * @param wristPosition in encoder counts
     * @param mastPosition in encoder counts
     */
    public Place(int elevatorPos, int wristPosition, int mastPosition) {
        addSequential(new Interrupt());

        addParallel(new MastPosition(mastPosition)); // move the mast out to the pos
        addSequential(new ElevatorPosition(elevatorPos)); // move the elevator
        addSequential(new WristPosition(wristPosition)); // move the wrist
    }

}