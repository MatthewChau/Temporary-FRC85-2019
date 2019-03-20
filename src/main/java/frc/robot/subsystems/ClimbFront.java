package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.IMU;
import frc.robot.sensors.Sensors;

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
        //_climbFrontMotorLeft.setSmartCurrentLimit(0, 5700, 3000);
        _climbFrontMotorRight = new CANSparkMax(Addresses.CLIMB_FRONT_MOTOR_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        _climbFrontMotorRight.setInverted(true);
        _climbFrontMotorRight.setIdleMode(IdleMode.kBrake);
        //_climbFrontMotorRight.setSmartCurrentLimit(0, 5700, 3000);
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
                                                  Variables.getInstance().getClimbMaxSpeedUp() / 3,
                                                  Variables.getInstance().getClimbMaxSpeedDown() / 3);

        double speedLeft;
        double speedRight;

        // roll is positive clockwise

        if (modify > 0) {
            speedLeft = speed;
            speedRight = speed - modify;
        } else {
            speedLeft = speed + modify;
            speedRight = speed;
        }

        if (getClimbLeftPosition() > Variables.CLIMB_LEFT_SLOW_DOWN) {
            speedLeft *= 0.05;
        }

        if (getClimbRightPosition() > Variables.CLIMB_RIGHT_SLOW_DOWN) {
            speedRight *= 0.05;
        }

        if (Sensors.getInstance().getClimbLeftLimit() && speedLeft < 0
            || leftSoftLimits(speedLeft)) {
            speedLeft = 0;
        }

        if (Sensors.getInstance().getClimbRightLimit() && speedRight < 0
            || rightSoftLimits(speedRight)) {
            speedRight = 0;
        }
    
        setClimbLeftMotor(speedLeft);
        setClimbRightMotor(speedRight);
    }

    public boolean leftSoftLimits(double speed) {
        if (getClimbLeftPosition() > Variables.CLIMB_MAX && speed > 0) {
            return true;
        }
        if (getClimbLeftPosition() < Variables.CLIMB_MIN && speed < 0) {
            return true;
        }
        return false;
    }

    public boolean rightSoftLimits(double speed) {
        if (getClimbRightPosition() > Variables.CLIMB_MAX && speed > 0) {
            return true;
        }
        if (getClimbRightPosition() < Variables.CLIMB_MIN && speed < 0) {
            return true;
        }
        return false;
    }

    public void setClimbLeftMotor(double speed) {
        _climbFrontMotorLeft.set(speed);
    }

    public void setClimbRightMotor(double speed) {
        _climbFrontMotorRight.set(speed);
    }

    public void setClimbFrontMotors(double speed) {
        double speedLeft = speed;
        double speedRight = speed;

        if (Sensors.getInstance().getClimbLeftLimit() && speedLeft < 0) {
            speedLeft = 0;
        }

        if (Sensors.getInstance().getClimbRightLimit() && speedRight < 0) {
            speedRight = 0;
        }

        _climbFrontMotorLeft.set(speedLeft);
        _climbFrontMotorRight.set(speedRight);
    }

    public double getClimbLeftPosition() {
        return _climbFrontMotorLeft.getEncoder().getPosition();
    }

    public double getClimbRightPosition() {
        return _climbFrontMotorRight.getEncoder().getPosition();
    }

    public void setClimbLeftPosition(double position) {
        _climbFrontMotorLeft.setEncPosition(position);
    }

    public void setClimbRightPosition(double position) {
        _climbFrontMotorRight.setEncPosition(position);
    }

    public void setClimbFrontEncoders(double position) {
        _climbFrontMotorLeft.setEncPosition(position);
        _climbFrontMotorRight.setEncPosition(position);
    }

}