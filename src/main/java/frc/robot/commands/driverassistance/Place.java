package frc.robot.commands.driverassistance;

import java.lang.reflect.Executable;

import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.lift.WaitForElevator;
import frc.robot.subsystems.Elevator;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Place extends CommandGroup {

    public Place(int liftPos, int intakePos/*, int mastPos*/) {
        addSequential(new Interrupt());

        addSequential(new WaitForElevator(0.3)); // wait with the pid running
        addParallel(new WristPosition(intakePos));
        //addParallel(new MastPosition(mastPos));
        addSequential(new ElevatorPosition(liftPos));

        //addSequential(new WaitButton(!OI.getInstance().getOperatorHatchRelease()));
    }

}