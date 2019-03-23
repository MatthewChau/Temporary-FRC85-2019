package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.IMU;
import frc.robot.sensors.Sensors;
import frc.robot.commands.climb.ClimbRearDriveWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ClimbRearDrive extends Subsystem {

    private static ClimbRearDrive _instance = null;

    private TalonSRX _climbRearDriveMotor;

    private ClimbRearDrive() {
        _climbRearDriveMotor = new TalonSRX(Addresses.CLIMB_REAR_DRIVE);
        _climbRearDriveMotor.setNeutralMode(NeutralMode.Brake);
    }

    public static ClimbRearDrive getInstance() {
        if (_instance == null) {
            _instance = new ClimbRearDrive();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ClimbRearDriveWithJoystick());
    }
    
    // CLIMB DRIVE MOTOR

    public void moveClimbRearDrive(double speed) {
        // decide that pid exists here maybe?
    }

    public void setClimbRearDriveMotor(double speed) {
        if (Math.abs(speed) < 0.1) {
            speed = 0;
        }
        
        _climbRearDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public int getClimbRearDriveMotorPosition() {
        return _climbRearDriveMotor.getSelectedSensorPosition();
    }

    public void setClimbRearDriveMotorPosition(int position) {
        _climbRearDriveMotor.setSelectedSensorPosition(position);
    }

}