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

    public PlaceHatch(int liftPosition) {
        addSequential(new Wait(0.3));
        addParallel(new IntakePosition(-500000)); //Need to move lift horizontally backwards while at the same time set the flipper position back to zero
        addSequential(new LiftVerticalPosition(LiftVertical.getInstance().getVerticalPosition() - 5000)); 
        addSequential(new DriveSeconds(0.2, 5));
    }
}