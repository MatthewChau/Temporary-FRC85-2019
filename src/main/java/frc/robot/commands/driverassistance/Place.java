package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.subsystems.Elevator;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;

public class Place extends CommandGroup {

    public Place(int liftPos, int intakePos) {
        addParallel(new WristPosition(intakePos));
        addSequential(new ElevatorPosition(liftPos));

        //addSequential(new WaitButton(!OI.getInstance().getOperatorHatchRelease()));
    }
}