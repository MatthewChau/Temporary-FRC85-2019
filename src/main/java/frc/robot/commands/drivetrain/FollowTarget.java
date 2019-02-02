package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command {
    private double _distanceWanted = 35.0; //The distance from the pads that we want the robot to be
    private double _tolerance = 5.0;

    private double[] _driveForward = new double[] {0, 0, -.25, 0, 0};
    private double[] _driveBackward = new double[] {0, 0, .25, 0, 0};
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
        if (35 <= Vision.distance() && Vision.distance() < 45) {
            DriveTrain.getInstance().cartDrive(_stop); //assuming backwards is positive
            //_targetFound = true;
        }
        else if (Vision.distance() < 35) {
            DriveTrain.getInstance().cartDrive(_driveBackward);  //assuming forwards is negative
        }
        else if (Vision.distance() >= 45) {
            DriveTrain.getInstance().cartDrive(_driveForward);
        }
        else{
            DriveTrain.getInstance().cartDrive(_stop);
        }
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