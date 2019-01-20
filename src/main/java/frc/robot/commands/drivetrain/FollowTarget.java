package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command{


    public FollowTarget(){
        requires(DriveTrain.getInstance());
    }

    protected void initialize(){
        super.initialize();
        DriveTrain.getInstance().followTarget();
    }



    protected boolean isFinished(){ 
        return (Vision.centerX() < 10);//Ends command if within a certain range of error
    }

    protected void end(){
        super.end();
        DriveTrain.getInstance().mDrive(0, 0, 0);
    }

}