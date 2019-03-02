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
import frc.robot.commands.drivetrain.FollowOneTarget;;

public class PlaceHatch extends CommandGroup {

    public PlaceHatch(int liftPos, int intakePos) {
        addSequential(new Wait(0.3));
        addSequential(new IntakePosition(intakePos)); //Need to move lift horizontally backwards while at the same time set the flipper position back to zero
        addSequential(new LiftVerticalPosition(liftPos));
    }
}