package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class FrontClimb extends Subsystem {

    private static FrontClimb _instance = null;

    private CANSparkMax _frontClimbMotorLeft, _frontClimbMotorRight;

    private FrontClimb() {
        _frontClimbMotorLeft = new CANSparkMax(Addresses.FRONT_CLIMB_MOTOR_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        _frontClimbMotorLeft.setIdleMode(IdleMode.kBrake);
        _frontClimbMotorRight = new CANSparkMax(Addresses.FRONT_CLIMB_MOTOR_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        _frontClimbMotorRight.setIdleMode(IdleMode.kBrake);
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
        double modify = OI.getInstance().applyPID(OI.CLIMB_SYSTEM, 
                                                  IMU.getInstance().getRoll(), 
                                                  0.0, 
                                                  Variables.getInstance().getClimbkP(), 
                                                  Variables.getInstance().getClimbkI(),
                                                  Variables.getInstance().getClimbkD(), 
                                                  0.1,
                                                  -0.1);
    
        setFrontClimbLeftMotor(speed);
        setFrontClimbRightMotor(speed - modify);
    }

    /* aight friendos let's talk about how dumb this is going to be
       basically we need a pid loop to keep us stable
       one between front and back
       one between left and right
    */

    public void setFrontClimbLeftMotor(double speed) {
        _frontClimbMotorLeft.set(speed);
    }

    public void setFrontClimbRightMotor(double speed) {
        _frontClimbMotorRight.set(speed);
    }

    public void setFrontClimbMotors(double speed) {
        _frontClimbMotorLeft.set(speed);
        _frontClimbMotorRight.set(speed);
    }

    public double getFrontClimbLeftPosition() {
        return _frontClimbMotorLeft.getEncoder().getPosition();
    }

    public double getFrontClimbRightPosition() {
        return _frontClimbMotorRight.getEncoder().getPosition();
    }

    public void setFrontClimbOnePosition(double position) {
        _frontClimbMotorLeft.setEncPosition(position);
    }

    public void setFrontClimbTwoPosition(double position) {
        _frontClimbMotorRight.setEncPosition(position);
    }

    public void setFrontClimbEncoders(double position) {
        _frontClimbMotorLeft.setEncPosition(position);
        _frontClimbMotorRight.setEncPosition(position);
    }

}