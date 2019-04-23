package frc.robot.commands.driverassistance;

import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.intake.ActivateWrist;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.lift.ActivateMast;
import frc.robot.commands.lift.ActivateElevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Place extends CommandGroup {

    /**
     * @param elevatorPosition in encoder counts
     * @param wristPosition in encoder counts
     * @param mastPosition in encoder counts
     */
    public Place(int elevatorPos, int wristPosition, int mastPosition) {
        addSequential(new Interrupt());

        /*if (mastPosition == 0) // if zeroing the mast
            addParallel(new ActivateMast(-0.8)); // run back until limit
        else */
            addParallel(new MastPosition(mastPosition)); // move mast to position

        /*if (elevatorPos == 0) // if zeroing the elevator
            addSequential(new ActivateElevator (-0.4)); // run down until limit
        else*/
            addSequential(new ElevatorPosition(elevatorPos)); // move the elevator to position

        /*if (wristPosition == 0) // if zeroing the wrist
            addSequential(new ActivateWrist(0.7)); // run up until limit
        else*/
            addSequential(new WristPosition(wristPosition)); // move the wrist to position
    }

}