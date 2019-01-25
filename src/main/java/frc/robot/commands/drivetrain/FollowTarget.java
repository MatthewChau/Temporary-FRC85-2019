package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command {
    private double _distanceWanted = 24.0; //The distance from the pads that we want the robot to be
    private double _tolerance = 2.0;
    @Override
    protected void initialize() {
        super.initialize();
        requires(DriveTrain.getInstance());
    }

    @Override
    protected void execute() {
        super.execute();
        if (Vision.distance() <= (_distanceWanted - _tolerance)) {
            DriveTrain.getInstance().mDrive(0.5, 0.5, 0); //assuming backwards is positive
        }
        else if (Vision.distance() >= (_distanceWanted + _tolerance)) {
            DriveTrain.getInstance().mDrive(-0.5, -0.5, 0); //assuming forwards is negative
        }
        else {
            DriveTrain.getInstance().mDrive(0, 0, 0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public synchronized void cancel() { //I think that this is called when the command is cancelled
        super.cancel();
        DriveTrain.getInstance().mDrive(0, 0, 0);
    }

    @Override
    protected void end() {
        super.end();
        DriveTrain.getInstance().mDrive(0, 0, 0);
    }
}