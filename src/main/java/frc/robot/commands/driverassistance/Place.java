package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.IntakePosition;
import frc.robot.commands.lift.LiftHorizontalPosition;
import frc.robot.commands.lift.LiftVerticalPosition;
import frc.robot.subsystems.LiftVertical;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;

public class Place extends CommandGroup {

    public Place(int liftPos, int intakePos) {
        addParallel(new IntakePosition(intakePos));
        addSequential(new LiftVerticalPosition(liftPos));

        //addSequential(new WaitButton(!OI.getInstance().getOperatorHatchRelease()));
    }
}