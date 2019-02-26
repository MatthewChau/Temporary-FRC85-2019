package frc.robot.commands.driverassistance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.IntakePosition;
import frc.robot.commands.lift.HorizontalShift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;;

public class PlaceHatch extends CommandGroup {
    public PlaceHatch(int liftPosition, int rotateIntakeToThis) {
        addSequential(new FollowOneTarget(), 3);
        addSequential(new FollowTwoTarget(), 3);
        addSequential(new VerticalShift(liftPosition, 0.3));
        addSequential(new IntakePosition(rotateIntakeToThis));
        addSequential(new HorizontalShift(9000, 0.25)); //9000 is just a random number for now
        addParallel(new IntakePosition(0)); //Need to move lift horizontally backwards while at the same time set the flipper position back to zero
        addSequential(new HorizontalShift(0, 0.25));
        addSequential(new VerticalShift(0, 0.3));
    }
}