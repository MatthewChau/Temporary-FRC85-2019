package frc.robot.commands.driverassistance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.IntakePosition;
import frc.robot.commands.lift.LiftVerticalPosition;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;
import frc.robot.commands.intake.ActivateIntake;
import frc.robot.commands.driverassistance.Wait;

public class PlaceCargo extends CommandGroup {
    public PlaceCargo(int liftPosition, int rotateIntakeToThis) {
        addSequential(new FollowOneTarget(), 3);
        addSequential(new FollowTwoTarget(), 3);
        addSequential(new LiftVerticalPosition(liftPosition));
        addSequential(new IntakePosition(rotateIntakeToThis));
        addSequential(new ActivateIntake(-.5));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(Variables.getInstance().INTAKE_DEGREE_ONE));
        addParallel(new IntakePosition(0)); //Need to move lift horizontally backwards while at the same time set the flipper position back to zero
        addSequential(new LiftVerticalPosition(Variables.getInstance().CARGO_FLOOR));
    }
}