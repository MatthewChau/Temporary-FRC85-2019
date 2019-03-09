package frc.robot.subsystems;
   
import frc.robot.Addresses;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class RearClimb extends Subsystem {

    private static RearClimb _instance = null;

    private CANSparkMax _rearClimbMotor;

    private TalonSRX _rearClimbDriveMotor;

    private RearClimb() {
        _rearClimbMotor = new CANSparkMax(Addresses.REAR_CLIMB_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

        _rearClimbDriveMotor = new TalonSRX(Addresses.REAR_CLIMB_MOTOR);
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

    public void setRearClimbMotor(double speed) {
        _rearClimbMotor.set(speed);
    }

    public int getRearClimbPosition() {
        return 0;
    }

    public void setRearClimbPosition(int position) {
    }

}