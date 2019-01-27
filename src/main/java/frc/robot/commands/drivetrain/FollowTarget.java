package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command {
    private double _distanceWanted = 24.0; //The distance from the pads that we want the robot to be
    private double _tolerance = 2.0;

    private double[] _driveForward = new double[] {0, .05, 0, 0, 0};
    private double[] _driveBackward = new double[] {0, .05, 0, 0, 0};
    private double[] _stop = new double[] {0, 0, 0, 0, 0};

    private boolean _targetFound;
    
    @Override
    protected void initialize() {
        super.initialize();
        requires(DriveTrain.getInstance());
    }

    @Override
    protected void execute() {
        super.execute();
        if (Vision.distance() <= (_distanceWanted - _tolerance)) {
            DriveTrain.getInstance().cartDrive(_driveForward); //assuming backwards is positive
        }
        else if (Vision.distance() >= (_distanceWanted + _tolerance)) {
            DriveTrain.getInstance().cartDrive(_driveBackward);  //assuming forwards is negative
        }
        else {
            DriveTrain.getInstance().cartDrive(_stop);
            _targetFound = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return _targetFound;
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