package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;
import frc.robot.sensors.IMU;

public class FollowOneTarget extends Command {
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
        double xSpeed, ySpeed, zRotation, gyroAngle; // the angle only added in order to stay straight while strafing
        double kPVision = 0.01;
        double kIVision = 0.0;
        double kDVision = 0.0;
        double kPVisionRot = 0.012, kIVisionRot = 0.0, kDVisionRot = 0.2;

        // x & rotation together should be enough to align, honestly

        xSpeed = -OI.getInstance().applyPID(OI.getInstance().VISION_X_SYSTEM, Vision.getInstance().oneTargetCenter(), 120.0, kPVision, kIVision, kDVision, .25, -.25); // 120 appears to be the center
        ySpeed = 0.0;
        zRotation = -OI.getInstance().applyPID(OI.getInstance().VISION_ROT_SYSTEM, Vision.getInstance().oneTargetAngle(), 0.0, kPVisionRot, kIVisionRot, kDVisionRot, .5, -.5);
        gyroAngle = IMU.getInstance().getFusedHeading(); // will take this out eventually

        SmartDashboard.putNumber("Rotation For 1 Target Vision", zRotation);

        double[] _speedArray = {xSpeed, ySpeed, zRotation, gyroAngle};
        DriveTrain.getInstance().cartDrive(_speedArray);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
    }

    @Override
    protected void end() {
        super.end();
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
    }
}