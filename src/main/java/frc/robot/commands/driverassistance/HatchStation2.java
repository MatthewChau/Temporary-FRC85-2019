package frc.robot.commands.driverassistance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.intake.WristPosition;
import frc.robot.commands.lift.ElevatorPosition;

public class HatchStation2 extends CommandGroup {
    public HatchStation2() {
        addSequential(new Interrupt());
        addSequential(new WristPosition(0)); //Default position of intake
        addSequential(new ElevatorPosition(Variables.getInstance().HATCH_TWO));
    }
}