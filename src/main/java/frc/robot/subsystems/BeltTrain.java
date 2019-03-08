package frc.robot.subsystems;
    
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.commands.belttrain.BeltTrainWithJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class BeltTrain extends Subsystem {
    
    public static BeltTrain _instance = null;

    private TalonSRX _leftMotor, _rightMotor;

    public BeltTrain() {
        _leftMotor = new TalonSRX(Addresses.BELTTRAIN_LEFT_MOTOR);
        _leftMotor.setNeutralMode(NeutralMode.Coast);
        _rightMotor = new TalonSRX(Addresses.BELTTRAIN_RIGHT_MOTOR);
        _rightMotor.setNeutralMode(NeutralMode.Coast);
    }

    public static BeltTrain getInstance() {
        if (_instance == null) {
            _instance = new BeltTrain();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new BeltTrainWithJoystick());
    }

    public void DriveBelt(double speed) {
        _leftMotor.set(ControlMode.PercentOutput, speed);
        _rightMotor.set(ControlMode.PercentOutput, -speed);
    }

    public double getLeftMotorPercent() {
        return _leftMotor.getMotorOutputPercent();
    }

    public double getRightMotorPercent() {
        return _rightMotor.getMotorOutputPercent();
    }

    public double getLeftMotorVoltage() {
        return _leftMotor.getMotorOutputVoltage();
    }

    public double getRightMotorVoltage() {
        return _rightMotor.getMotorOutputVoltage();
    }
     
}
