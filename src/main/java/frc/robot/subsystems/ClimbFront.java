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
            speedRight = speed; // bc modify will be negative
        }

        if (Sensors.getInstance().getClimbLeftLimit() && speedLeft > 0) {
            speedLeft = 0;
        }

        if (Sensors.getInstance().getClimbRightLimit() && speedRight > 0) {
            speedRight = 0;
        }
    
        setClimbLeftMotor(speedLeft);
        setClimbRightMotor(speedRight);
    }

    /*public boolean leftSoftLimits() {
        if (getClimbLeftPosition() > Variables.MAX_NEO_POS) {
            return true;
        }
        if () {
            return true;
        }
    }*/

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