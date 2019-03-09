package frc.robot.commands.driverassistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SendItBro extends CommandGroup {

    public SendItBro() {
        addSequential(new Interrupt());
    }
    
}