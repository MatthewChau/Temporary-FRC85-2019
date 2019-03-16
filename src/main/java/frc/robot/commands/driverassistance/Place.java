package frc.robot.commands.driverassistance;

import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorLock;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.lift.WaitForElevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Place extends CommandGroup {

    /**
     * @param elevatorPosition in encoder counts
     * @param wristPosition in encoder counts
     * @param mastPosition in encoder counts
     */
    public Place(int elevatorPos, int wristPosition/*, int mastPosition*/) {
        addSequential(new Interrupt());

        addSequential(new WaitForElevator(0.3)); // wait with the pid running
        addSequential(new WristPosition(wristPosition));
        addSequential(new ElevatorPosition(elevatorPos));
        //addParallel(new MastPosition(mastPosition));
        addSequential(new ElevatorLock(), 0.3);
    }

}