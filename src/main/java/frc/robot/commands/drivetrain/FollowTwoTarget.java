package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTwoTarget extends Command {
    private double[] _stop = new double[] {0, 0, 0, 0};

    //private boolean _targetFound;
    
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
        double kPVision = 0.027;
        double kIVision = 0.0;
        double kDVision = 0.0;
        double kPVisionRot = 0.012, kIVisionRot = 0.0, kDVisionRot = 0.2;

        xSpeed = OI.getInstance().applyPID(OI.getInstance().VISION_X_SYSTEM, Vision.getInstance().twoTargetCenter(), targetCenter, kPVision, kIVision, kDVision, .5, -.5);
        ySpeed = OI.getInstance().applyPID(OI.getInstance().VISION_Y_SYSTEM, Vision.getInstance().twoTargetDistance(), targetDistance, kPVision, kIVision, kDVision, .5, -.5);
        zRotation = -OI.getInstance().applyPID(OI.getInstance().VISION_ROT_SYSTEM, Vision.getInstance().getAreaDifference(), 0.0, kPVisionRot, kIVisionRot, kDVisionRot, .5, -.5);
        
        SmartDashboard.putNumber("Rotation For 2 Target Vision", zRotation);

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
        DriveTrain.getInstance().cartDrive(_stop);
    }

    @Override
    protected void end() {
        super.end();
        DriveTrain.getInstance().cartDrive(_stop);
    }
}