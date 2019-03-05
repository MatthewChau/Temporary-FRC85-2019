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
import frc.robot.commands.driverassistance.Interrupt;

//This is a test command group to see how the system reacts to being sent command groups on button hold
public class Testing extends CommandGroup {
    public Testing() {
        //add this at the beginning of any command group if you want to be able to interupt it
        addSequential(new Interrupt());
        
        addSequential(new ActivateIntake(-.3));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(0));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(.3));
        addSequential(new Wait(3));
        addSequential(new ActivateIntake(0));
    }
}