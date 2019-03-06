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
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.commands.intake.ActivateWrist;
import frc.robot.commands.lift.ActivateMast;
import frc.robot.commands.lift.ActivateElevator;
import frc.robot.OI;
import frc.robot.commands.lift.MastPosition;


//sequence of commands used for loading hatch panels from ground

// BROOKE AND SCOTT WERE HERE, HIIIIIIIIIIIIIII
// Started a sequence to pick up hatch panels from ground by moving to a safe position.
// ... The last addsequential will not run unless the others are commented out so
// we think we might be dealing with some limit issues on the prev. addsequential.
public class HatchGround extends CommandGroup {
    public HatchGround() {
        addSequential(new Interrupt());

        addParallel(new ElevatorPosition(Variables.getInstance().CARGO_ONE));
        addSequential(new MastPosition(Variables.getInstance().MAST_MAX_POS));
        addSequential(new WristPosition(Variables.getInstance().Wrist_Floor_Pickup_Pos));



    }
}