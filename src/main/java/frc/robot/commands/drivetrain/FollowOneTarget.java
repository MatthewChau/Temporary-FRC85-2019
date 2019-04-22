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
    private double _targetAngle;

    private static final double[] TARGET_ANGLES = {0, 30, -30, 60, -60, 90, -90, 150, -150, 180};
    private double[] _errors = new double[TARGET_ANGLES.length];

    private int index = 0;
    private double minimum = 2520;
    
    public FollowOneTarget() {
        requires(DriveTrain.getInstance());
    }
    
    @Override
    protected void initialize() {
        int i;

        Spike.getInstance().setRelay(true);

        double _currentAngle = IMU.getInstance().getFusedHeading();

        _currentAngle = _currentAngle %360; //Finds the modulus (does long division and only returns the remainder)

        for (i=0; i<TARGET_ANGLES.length; i++) {
            _errors[i] = Math.abs(TARGET_ANGLES[i] - _currentAngle);

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

        double targetCenter = 160.0;

        xSpeed = -OI.getInstance().applyPID(OI.VISION_X_SYSTEM,
                                           Vision.getInstance().oneTargetCenter(), 
                                           targetCenter, 
                                           Variables.getInstance().getVisionKP(), 
                                           Variables.getInstance().getVisionKI(), 
                                           Variables.getInstance().getVisionKD(), 
                                           Variables.getInstance().getVisionMaxSpeed(), 
                                           -Variables.getInstance().getVisionMaxSpeed());

        ySpeed = OI.getInstance().getLeftYInputJoystick();

        SmartDashboard.putNumber("xSpeed Vision", xSpeed);

        zRotation = 0; /*OI.getInstance().applyPID(OI.VISION_ROT_SYSTEM,
                                              Vision.getInstance().oneTargetAngle(), 
                                              0.0,
                                              Variables.getInstance().getVisionRotKP(),
                                              Variables.getInstance().getVisionRotKI(),
                                              Variables.getInstance().getVisionRotKD(),
                                              Variables.getInstance().getVisionMaxSpeed(),
                                              -Variables.getInstance().getVisionMaxSpeed());*/

        double[] _speedArray = {xSpeed, ySpeed, zRotation, gyroAngle};
        DriveTrain.getInstance().cartDrive(_speedArray);
    }

    @Override
    protected boolean isFinished() {
        return !OI.getInstance().getRightStickThumbButton();
    }

    @Override
    public synchronized void cancel() {
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
        Spike.getInstance().setRelay(false);
    }

    @Override
    protected void end() {
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
        Spike.getInstance().setRelay(false);
    }
    
}