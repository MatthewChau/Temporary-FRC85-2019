package frc.robot.commands.drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.subsystems.DriveTrain;

public class FollowTarget extends Command {
    private double _distanceWanted = 35.0; //The distance from the pads that we want the robot to be
    private double _tolerance = 5.0;

    private double[] _stop = new double[] {0, 0, 0, 0};

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
        double zRotation = Vision.rotate();
        double targetDistance = 40.0;//SmartDashboard.getNumber("Target Distance", 0.0);
        double targetCenter = 0.0;//SmartDashboard.getNumber("Target Center", 0.0);
        double kPVision = 0.1;//SmartDashboard.getNumber("kPVision", 0.0);
        double kIVision = 0.0;//SmartDashboard.getNumber("kIVision", 0.0);
        double kDVision = 0.0;//SmartDashboard.getNumber("kDVision", 0.0);



        /*//Determine ySpeed
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

        //Determine xSpeed
        if (Vision.centerX() < -5) {
            xSpeed = 0.3;
        }
        else if (Vision.centerX() > 5) {
            xSpeed = -0.3;
        }
        else {
            xSpeed = 0;
        }*/

        xSpeed = OI.getInstance().applyPID(OI.getInstance().VISION_SYSTEM, Vision.centerX(), targetCenter, kPVision, kIVision, kDVision, .25, -.25);
        ySpeed = OI.getInstance().applyPID(OI.getInstance().LIFT_UPDOWN_SYSTEM, Vision.distance(), targetDistance, kPVision, kIVision, kDVision, .25, -.25);
        

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