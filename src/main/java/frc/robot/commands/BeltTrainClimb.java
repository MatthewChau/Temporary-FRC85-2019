
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.BeltTrain;

public class BeltTrainClimb extends Command {
    public BeltTrainClimb() {
    
        requires(BeltTrain.getInstance());

    }

  @Override
  protected void initialize() {
    
    super.initialize();
  
  }


  @Override
  protected void execute() {
   
    super.execute();
  
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
  @Override
  protected void end() {
   
    super.end();
 
  }


  @Override
  protected void interrupted() {
  }
}
