package frc.robot.commands.driverassistance;

import frc.robot.commands.lift.ActivateMast;
import frc.robot.commands.intake.ActivateWrist;
import frc.robot.commands.lift.ActivateElevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ZeroSystems extends CommandGroup {

    /**
     * @param elevatorPosition in encoder counts
     * @param wristPosition in encoder counts
     * @param mastPosition in encoder counts
     */
    public ZeroSystems() {
        addSequential(new Interrupt());

        addSequential(new ActivateWrist(0.7)); // run wrist up until limit
        addParallel(new ActivateMast(-0.8)); // run back until limit
        addSequential(new ActivateElevator(-0.4)); // run elevator down until limit
    }

}