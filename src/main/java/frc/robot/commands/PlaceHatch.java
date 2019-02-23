package frc.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.IntakePosition;
import frc.robot.commands.lift.HorizontalShift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.drivetrain.DriveSeconds;

public class PlaceHatch extends CommandGroup {
    public PlaceHatch(int position) {
        addSequential(new FollowTwoTarget(), 3);
        addSequential(new VerticalShift(position, 0.3));
        addSequential(new IntakePosition(3000)); //3000 is just a random number for now
        addSequential(new HorizontalShift(9000, 0.25)); //9000 is just a random number for now
        addParallel(new IntakePosition(0)); //Need to move lift horizontally backwards while at the same time set the flipper position back to zero
        addSequential(new HorizontalShift(0, 0.25));
        addSequential(new VerticalShift(0, 0.3));
    }
}

