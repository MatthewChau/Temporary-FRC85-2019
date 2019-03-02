package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.IntakePosition;
import frc.robot.commands.lift.LiftHorizontalPosition;
import frc.robot.commands.lift.LiftVerticalPosition;
import frc.robot.subsystems.LiftHorizontal;
import frc.robot.subsystems.LiftVertical;
import frc.robot.subsystems.Intake;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;;

public class HatchRelease extends CommandGroup {

    public HatchRelease() { // this doesn't work ok, will redo
        addParallel(new IntakePosition(Intake.getInstance().getFlipperPosition() - 100000));
        addParallel(new LiftVerticalPosition(LiftVertical.getInstance().getVerticalPosition() - 1000));
        addSequential(new LiftHorizontalPosition(LiftHorizontal.getInstance().getHorizontalPosition() - 10000));
    }
}