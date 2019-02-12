package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowOneTarget extends Command {
    private double[] _stop = new double[] {0, 0, 0, 0};

    //private boolean _targetFound;
    
    public FollowOneTarget() {
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

        xSpeed = 0.0;
        ySpeed = 0.0;
        zRotation = -OI.getInstance().applyPID(OI.getInstance().VISION_ROT_SYSTEM, Vision.getInstance().oneTargetAngle(), 0.0, kPVisionRot, kIVisionRot, kDVisionRot, .5, -.5);
        
        SmartDashboard.putNumber("Rotation For 1 Target Vision", zRotation);

        double[] _speedArray = {-xSpeed, ySpeed, zRotation, 0};
        DriveTrain.getInstance().cartDrive(_speedArray);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
        DriveTrain.getInstance().cartDrive(_stop);
    }

    @Override
    protected void end() {
        super.end();
        DriveTrain.getInstance().cartDrive(_stop);
    }
}