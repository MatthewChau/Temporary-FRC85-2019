package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command {
    private double _distanceWanted = 35.0; //The distance from the pads that we want the robot to be
    private double _tolerance = 5.0;

    private double[] _driveForward = new double[] {0, 0, -.25, 0, 0};
    private double[] _driveBackward = new double[] {0, 0, .25, 0, 0};
    private double[] _driveLeft = new double[] {0, .25, 0, 0, 0};
    private double[] _driveRight = new double[] {0, -.25, 0, 0, 0};

    private double[] _stop = new double[] {0, 0, 0, 0, 0};

    private boolean _targetFound;
    
    public FollowTarget() {
        requires(DriveTrain.getInstance());
    }
    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
        double xSpeed, ySpeed;

        if (35 <= Vision.distance() && Vision.distance() <= 45) {
            ySpeed = 0;
        }
        else if (Vision.distance() < 35) {

            ySpeed = 0.3;
        }
        else if (Vision.distance() > 45) {
            ySpeed = -0.3;
        }
        else{
            ySpeed = 0;
        }

        if (Vision.centerX() < -5) {
            xSpeed = 0.4;
        }
        else if (Vision.centerX() > 5) {
                xSpeed = -0.4;
        }
        else {
            xSpeed = 0;
        }

        double[] _speedArray = {0, xSpeed, ySpeed, 0, 0};
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