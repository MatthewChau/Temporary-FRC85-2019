package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class ClimbFront extends Subsystem {

    private static ClimbFront _instance = null;

    private CANSparkMax _climbFrontMotorLeft, _climbFrontMotorRight;

    private ClimbFront() {
        _climbFrontMotorLeft = new CANSparkMax(Addresses.CLIMB_FRONT_MOTOR_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        _climbFrontMotorLeft.setIdleMode(IdleMode.kBrake);
        _climbFrontMotorRight = new CANSparkMax(Addresses.CLIMB_FRONT_MOTOR_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        _climbFrontMotorRight.setIdleMode(IdleMode.kBrake);
    }

    public static ClimbFront getInstance() {
        if (_instance == null) {
            _instance = new ClimbFront();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void moveClimbFront(double speed) {
        double modify = OI.getInstance().applyPID(OI.CLIMB_SYSTEM, 
                                                  IMU.getInstance().getRoll(), 
                                                  0.0, 
                                                  Variables.getInstance().getClimbkP(), 
                                                  Variables.getInstance().getClimbkI(), 
                                                  Variables.getInstance().getClimbkD(),
                                                  Variables.getInstance().getClimbMaxSpeedUp() / 5,
                                                  Variables.getInstance().getClimbMaxSpeedDown() / 5);
    
        setClimbFrontLeftMotor(speed);
        setClimbFrontRightMotor(speed - modify);
    }

    /* aight friendos let's talk about how dumb this is going to be
       basically we need a pid loop to keep us stable
       one between front and back
       one between left and right
    */

    public void setClimbFrontLeftMotor(double speed) {
        _climbFrontMotorLeft.set(speed);
    }

    public void setClimbFrontRightMotor(double speed) {
        _climbFrontMotorRight.set(speed);
    }

    public void setClimbFrontMotors(double speed) {
        _climbFrontMotorLeft.set(speed);
        _climbFrontMotorRight.set(speed);
    }

    public double getClimbFrontLeftPosition() {
        return _climbFrontMotorLeft.getEncoder().getPosition();
    }

    public double getClimbFrontRightPosition() {
        return _climbFrontMotorRight.getEncoder().getPosition();
    }

    public void setClimbFrontOnePosition(double position) {
        _climbFrontMotorLeft.setEncPosition(position);
    }

    public void setClimbFrontTwoPosition(double position) {
        _climbFrontMotorRight.setEncPosition(position);
    }

    public void setClimbFrontEncoders(double position) {
        _climbFrontMotorLeft.setEncPosition(position);
        _climbFrontMotorRight.setEncPosition(position);
    }

}