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
    private double _targetAngle;

    private static final double[] TARGET_ANGLES = {0, 30, -30, 60, -60, 90, -90, 150, -150, 180};
    private double[] _errors = new double[TARGET_ANGLES.length];

    private int index = 0;
    private double minimum = 360;
    
    public FollowOneTarget() {
        requires(DriveTrain.getInstance());
    }
    
    @Override
    protected void initialize() {
        Spike.getInstance().setRelay(true);

        for (int i=0; i<TARGET_ANGLES.length; i++) {
            _errors[i] = Math.abs(TARGET_ANGLES[i] - IMU.getInstance().getFusedHeading());

            if (_errors[i] < minimum) {
                minimum = _errors[i];
                index = i;
            }
        }

        _targetAngle = TARGET_ANGLES[index];
    }

    @Override
    protected void execute() {
        double xSpeed, ySpeed, zRotation, gyroAngle; // the angle only added in order to stay straight while strafing
        
        gyroAngle = IMU.getInstance().getFusedHeading();

        xSpeed = OI.getInstance().applyPID(OI.VISION_X_SYSTEM, 
            Vision.getInstance().oneTargetCenter(), 
            0.0, 
            Variables.getInstance().getVisionKP(), 
            Variables.getInstance().getVisionKI(), 
            Variables.getInstance().getVisionKD(),
            0.6,
            -0.6);

        ySpeed = OI.getInstance().getLeftYInputJoystick();

        zRotation = 0;

        DriveTrain.getInstance().setTargetAngle(_targetAngle);
        DriveTrain.getInstance().fixTargetAngle(gyroAngle);
        
        /*zRotation = OI.getInstance().applyPID(OI.ROT_SYSTEM,
            IMU.getInstance().getFusedHeading(),
            _targetAngle,
            Variables.getInstance().getVisionRotKP(),
            Variables.getInstance().getVisionRotKI(),
            Variables.getInstance().getVisionRotKD(),
            0.6,
            -0.6);*/

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