package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.OI;
import frc.robot.commands.drivetrain.FollowTwoTarget;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.MastPosition;
import frc.robot.commands.lift.ElevatorPosition;
import frc.robot.commands.intake.ActivateWrist;
import frc.robot.commands.lift.ActivateMast;
import frc.robot.commands.lift.ActivateElevator;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.commands.drivetrain.DriveSeconds;
import frc.robot.commands.drivetrain.FollowOneTarget;;

public class HatchRelease extends CommandGroup {

    public HatchRelease() {
        //addSequential(new Interrupt());

        //addSequential(new ElevatorPosition(Elevator.getInstance().getTargetPosition() - 1300));
        //addParallel(new MastPosition(Mast.getInstance().getHorizontalPosition() - 5000));
        addSequential(new ActivateWrist(-0.7, 0.25));
        //addSequential(new MastPosition(Variables.getInstance().MAST_PROTECTED));
    }

}