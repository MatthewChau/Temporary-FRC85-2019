package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.Variables;
import frc.robot.subsystems.DriveTrain;

public class FollowTwoTarget extends Command {
    
    public FollowTwoTarget() {
        requires(DriveTrain.getInstance());
    }
    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
        double xSpeed, ySpeed, zRotation;
        double targetDistance = 50.0;
        double targetCenter = 0.0;

        xSpeed = OI.getInstance().applyPID(OI.getInstance().VISION_X_SYSTEM,
                                           Vision.getInstance().twoTargetCenter(), 
                                           targetCenter, 
                                           Variables.getInstance().getVisionKP(), 
                                           Variables.getInstance().getVisionKI(), 
                                           Variables.getInstance().getVisionKD(), 
                                           .5, 
                                           -.5);
        
        ySpeed = OI.getInstance().applyPID(OI.getInstance().VISION_Y_SYSTEM, 
                                           Vision.getInstance().twoTargetDistance(), 
                                           targetDistance, 
                                           Variables.getInstance().getVisionKP(), 
                                           Variables.getInstance().getVisionKI(), 
                                           Variables.getInstance().getVisionKD(), 
                                           .5, 
                                           -.5);
        
        zRotation = -OI.getInstance().applyPID(OI.getInstance().VISION_ROT_SYSTEM, 
                                               Vision.getInstance().getAreaDifference(), 
                                               0.0, 
                                               Variables.getInstance().getVisionRotKP(), 
                                               Variables.getInstance().getVisionRotKI(), 
                                               Variables.getInstance().getVisionRotKD(), 
                                               .5, 
                                               -.5);
        
        double[] _speedArray = {-xSpeed, ySpeed, zRotation, 0};
        DriveTrain.getInstance().cartDrive(_speedArray);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public synchronized void cancel() { //I think that this is called when the command is cancelled
        super.cancel();
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
    }

    @Override
    protected void end() {
        super.end();
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
    }
    
}