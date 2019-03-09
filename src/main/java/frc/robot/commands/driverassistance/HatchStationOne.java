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

public class HatchStationOne extends CommandGroup {
    
    public HatchStationOne() {
        addSequential(new Interrupt());

        addParallel(new ElevatorPosition(Variables.HATCH_ONE));
        addSequential(new WristPosition(Variables.WRIST_30));
    }
}

