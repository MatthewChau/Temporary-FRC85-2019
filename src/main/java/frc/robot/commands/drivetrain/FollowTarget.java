package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command {
    private double[] _stop = new double[] {0, 0, 0, 0};

    private boolean _targetFound;
    
    public FollowTarget() {
        requires(DriveTrain.getInstance());
    }
    @Override
    protected void initialize() {
        SmartDashboard.putNumber("kPVIsion", 0.0);
        SmartDashboard.putNumber("kIVision", 0.0);
        SmartDashboard.putNumber("kDVision", 0.0);

        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
        double xSpeed, ySpeed, zRotation;
        double targetDistance = 40.0;//SmartDashboard.getNumber("Target Distance", 0.0);
        double targetCenter = 0.0;//SmartDashboard.getNumber("Target Center", 0.0);
        double kPVision = SmartDashboard.getNumber("kPVision", 0.0);
        double kIVision = SmartDashboard.getNumber("kIVision", 0.0);
        double kDVision = SmartDashboard.getNumber("kDVision", 0.0);

        xSpeed = OI.getInstance().applyPID(OI.getInstance().VISION_X_SYSTEM, Vision.getInstance().centerX(), targetCenter, kPVision, kIVision, kDVision, .5, -.5);
        ySpeed = OI.getInstance().applyPID(OI.getInstance().VISION_Y_SYSTEM, Vision.getInstance().distance(), targetDistance, kPVision, kIVision, kDVision, .5, -.5);
        zRotation = Vision.getInstance().rotate();

        SmartDashboard.putNumber("Rotation For Vision", zRotation);


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