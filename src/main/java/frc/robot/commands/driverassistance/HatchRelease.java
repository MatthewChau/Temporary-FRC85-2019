package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;;

public class HatchRelease extends CommandGroup {

    public HatchRelease() { // this doesn't work ok, will redo
        addParallel(new WristPosition(Intake.getInstance().getFlipperPosition() - 100000));
        addParallel(new ElevatorPosition(Elevator.getInstance().getVerticalPosition() - 1000));
        addSequential(new MastPosition(Mast.getInstance().getHorizontalPosition() - 10000));
    }
}