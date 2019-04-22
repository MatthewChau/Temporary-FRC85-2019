package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.Spike;

public class FollowOneTarget extends Command {
    //private boolean _targetFound;
    
    public FollowOneTarget() {
        requires(DriveTrain.getInstance());
    }
    @Override
    protected void initialize() {
        super.initialize();
        Spike.getInstance().setRelay(true);
    }

    @Override
    protected void execute() {
        super.execute();
        double xSpeed, ySpeed, gyroAngle; // the angle only added in order to stay straight while strafing
        
        gyroAngle = IMU.getInstance().getFusedHeading();

        xSpeed = -OI.getInstance().applyPID(OI.VISION_X_SYSTEM,
                                            Vision.getInstance().oneTargetCenter(), 
                                            Variables.CAMERA_CENTER, 
                                            Variables.getInstance().getVisionKP(), 
                                            Variables.getInstance().getVisionKI(), 
                                            Variables.getInstance().getVisionKD(), 
                                            Variables.getInstance().getVisionMaxSpeed(), 
                                            -Variables.getInstance().getVisionMaxSpeed());

        ySpeed = OI.getInstance().getLeftYInputJoystick();

        SmartDashboard.putNumber("xSpeed Vision", xSpeed);

        double[] _speedArray = {xSpeed, ySpeed, 0.0, gyroAngle};
        DriveTrain.getInstance().cartDrive(_speedArray);
    }

    @Override
    protected boolean isFinished() {
        return !OI.getInstance().getRightStickThumbButton();
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
        Spike.getInstance().setRelay(false);
    }

    @Override
    protected void end() {
        super.end();
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
        Spike.getInstance().setRelay(false);
    }
    
}