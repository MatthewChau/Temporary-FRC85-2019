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

public class ClimbRear extends Subsystem {

    private static ClimbRear _instance = null;

    private CANSparkMax _climbRearMotor;

    private TalonSRX _climbRearDriveMotor;

    private ClimbRear() {
        _climbRearMotor = new CANSparkMax(Addresses.CLIMB_REAR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        _climbRearMotor.setIdleMode(IdleMode.kBrake);
        _climbRearDriveMotor = new TalonSRX(Addresses.CLIMB_REAR_DRIVE);
        _climbRearDriveMotor.setNeutralMode(NeutralMode.Coast);
        _climbRearDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    public static ClimbRear getInstance() {
        if (_instance == null) {
            _instance = new ClimbRear();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }
    
    // CLIMB MOTOR

    public void moveClimbRear(double speed) {
        double modify = OI.getInstance().applyPID(OI.CLIMB_SYSTEM, 
                                                  IMU.getInstance().getPitch(), 
                                                  0.0, 
                                                  Variables.getInstance().getClimbkP(), 
                                                  Variables.getInstance().getClimbkI(), 
                                                  Variables.getInstance().getClimbkD(),
                                                  Variables.getInstance().getClimbMaxSpeedUp() / 5,
                                                  Variables.getInstance().getClimbMaxSpeedDown() / 5);

        setClimbRearMotor(speed - modify);
    }

    public void setClimbRearMotor(double speed) {
        _climbRearMotor.set(speed);
    }

    public double getClimbRearPosition() {
        return _climbRearMotor.getEncoder().getPosition();
    }

    public void setClimbRearPosition(double position) {
        _climbRearMotor.setEncPosition(position);
    }

    // CLIMB DRIVE MOTOR

    public void moveClimbRearDrive(double speed) {
        // decide that pid exists here
    }

    public void setClimbRearDriveMotor(double speed) {
        _climbRearDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public int getClimbRearDriveMotorPosition() {
        return _climbRearDriveMotor.getSelectedSensorPosition();
    }

    public void setClimbRearDriveMotorPosition(int position) {
        _climbRearDriveMotor.setSelectedSensorPosition(position);
    }

}