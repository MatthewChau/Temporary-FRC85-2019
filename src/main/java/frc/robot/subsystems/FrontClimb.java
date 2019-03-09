package frc.robot.subsystems;
   
import frc.robot.Addresses;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class FrontClimb extends Subsystem {

    private static FrontClimb _instance = null;

    private CANSparkMax _frontClimbMotorOne, _frontClimbMotorTwo;

//    private CANEncoder _frontClimbEncoderOne, _frontClimbEncoderTwo; // we don't apparently need to construct encoders?

    private FrontClimb() {
        _frontClimbMotorOne = new CANSparkMax(Addresses.FRONT_CLIMB_MOTOR_ONE, CANSparkMaxLowLevel.MotorType.kBrushless);
        _frontClimbMotorOne.setIdleMode(IdleMode.kBrake);
//        _frontClimbEncoderOne = new CANEncoder(_frontClimbMotorOne);
        _frontClimbMotorTwo = new CANSparkMax(Addresses.FRONT_CLIMB_MOTOR_TWO, CANSparkMaxLowLevel.MotorType.kBrushless);
        _frontClimbMotorOne.setIdleMode(IdleMode.kBrake);
//        _frontClimbEncoderTwo = new CANEncoder(_frontClimbMotorTwo);
    }

    public static FrontClimb getInstance() {
        if (_instance == null) {
            _instance = new FrontClimb();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void moveFrontClimb(double speed) {
        // more pid things i guess
    }

    public void setFrontClimbMotors(double speed) {
        _frontClimbMotorOne.set(speed);
        _frontClimbMotorTwo.set(speed);
    }

    public double getFrontClimbOnePosition() {
        return _frontClimbMotorOne.getEncoder().getPosition();
    }

    public double getFrontClimbTwoPosition() {
        return _frontClimbMotorTwo.getEncoder().getPosition();
    }

    public void setFrontClimbOnePosition(double position) {
        _frontClimbMotorOne.setEncPosition(position);
    }

    public void setFrontClimbTwoPosition(double position) {
        _frontClimbMotorTwo.setEncPosition(position);
    }

    public void setFrontClimbEncoders(double position) {
        _frontClimbMotorOne.setEncPosition(position);
        _frontClimbMotorTwo.setEncPosition(position);
    }

}