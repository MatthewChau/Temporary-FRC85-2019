package frc.robot.commands.driverassistance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Variables;
import frc.robot.commands.driverassistance.Interrupt;
import frc.robot.commands.intake.ActivateIntake;

//Sequence of commands used for loading cargo from the station
public class CargoStation2 extends CommandGroup {
    public CargoStation2() {
        addSequential(new Interrupt());
        addSequential(new ActivateIntake(0));
    }
}