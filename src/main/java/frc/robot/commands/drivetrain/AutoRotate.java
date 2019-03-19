package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Vision;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;

public class AutoRotate extends Command {
    private double gyroAngle = 0;
    private double rotateAngle = 0;

    public AutoRotate() {
        requires(DriveTrain.getInstance());
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    gyroAngle = IMU.getInstance().getFusedHeading();
    rotateAngle = Vision.getInstance().alignmentLine();
    DriveTrain.getInstance().cartDrive(0, 0, -rotateAngle, gyroAngle);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        DriveTrain.getInstance().cartDrive(OI.getInstance().stopArray);
    }

    @Override
    protected void interrupted() {
    }
}
