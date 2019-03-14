package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class RearClimb extends Subsystem {

    private static RearClimb _instance = null;

    private CANSparkMax _rearClimbMotor;

    private TalonSRX _rearClimbDriveMotor;

    private RearClimb() {
        _rearClimbMotor = new CANSparkMax(Addresses.REAR_CLIMB_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        _rearClimbMotor.setIdleMode(IdleMode.kBrake);
        _rearClimbDriveMotor = new TalonSRX(Addresses.REAR_CLIMB_MOTOR);
        _rearClimbDriveMotor.setNeutralMode(NeutralMode.Coast);
        _rearClimbDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    public static RearClimb getInstance() {
        if (_instance == null) {
            _instance = new RearClimb();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }
    
    // CLIMB MOTOR

    public void moveRearClimb(double speed) {
        double modify = OI.getInstance().applyPID(OI.CLIMB_SYSTEM, 
                                                  IMU.getInstance().getPitch(), 
                                                  0.0, 
                                                  Variables.getInstance().getClimbkP(), 
                                                  Variables.getInstance().getClimbkI(), 
                                                  Variables.getInstance().getClimbkD(),
                                                  0.1,
                                                  -0.1);

        setRearClimbMotor(speed - modify);
    }

    public void setRearClimbMotor(double speed) {
        _rearClimbMotor.set(speed);
    }

    public double getRearClimbPosition() {
        return _rearClimbMotor.getEncoder().getPosition();
    }

    public void setRearClimbPosition(double position) {
        _rearClimbMotor.setEncPosition(position);
    }

    // CLIMB DRIVE MOTOR

    public void moveRearClimbDrive(double speed) {
        // decide that pid exists here
    }

    public void setRearClimbDriveMotor(double speed) {
        _rearClimbDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public int getRearClimbDriveMotorPosition() {
        return _rearClimbDriveMotor.getSelectedSensorPosition();
    }

    public void setRearClimbDriveMotorPosition(int position) {
        _rearClimbDriveMotor.setSelectedSensorPosition(position);
    }

}