package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

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
        double xSpeed, ySpeed, zRotation, gyroAngle; // the angle only added in order to stay straight while strafing
        
        gyroAngle = IMU.getInstance().getFusedHeading();

        xSpeed = OI.getInstance().applyPID(OI.VISION_ROT_SYSTEM,
                                           Vision.getInstance().oneTargetCenter(), 
                                           0.0,
                                           Variables.getInstance().getVisionRotKP(),
                                           Variables.getInstance().getVisionRotKI(),
                                           Variables.getInstance().getVisionRotKD(),
                                           .6,
                                           -.6);

        ySpeed = OI.getInstance().getLeftYInputJoystick();

        zRotation = OI.getInstance().getRightRotJoystick();

        double[] _speedArray = {xSpeed, ySpeed, zRotation, gyroAngle};
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