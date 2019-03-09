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

//Sequence of commands used for loading cargo from the station
public class CargoStation1 extends CommandGroup {
    public CargoStation1() {
        addSequential(new Interrupt());
        addSequential(new ElevatorPosition(Variables.CARGO_ONE));
        addSequential(new WristPosition(0));
        addSequential(new ActivateIntake(-.33));
    }
}