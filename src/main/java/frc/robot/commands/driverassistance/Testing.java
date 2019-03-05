package frc.robot.commands.driverassistance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;
import frc.robot.commands.intake.ActivateIntake;
import frc.robot.commands.driverassistance.Wait;

//This is a test command group to see how the system reacts to being sent command groups on button hold
public class Testing extends CommandGroup {
    public Testing() {
        addSequential(new ActivateIntake(-.3));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(0));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(.3));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(0));
    }
}