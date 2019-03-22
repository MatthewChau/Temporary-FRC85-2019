package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.subsystems.ClimbRear;
import frc.robot.sensors.IMU;
import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class ClimbFront extends Subsystem {

    private static ClimbFront _instance = null;

    private CANSparkMax _climbFrontMotorLeft, _climbFrontMotorRight;

    private double targetPosition;
    private boolean adjusting = false;

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

    public void moveClimbFront(double speed) { // everything correctional is done here now
        double modifyRoll = OI.getInstance().applyPID(OI.CLIMB_SYSTEM, 
                                                      IMU.getInstance().getRoll(), 
                                                      0.0, 
                                                      Variables.getInstance().getClimbkP(), 
                                                      Variables.getInstance().getClimbkI(), 
                                                      Variables.getInstance().getClimbkD(),
                                                      Variables.getInstance().getClimbMaxSpeedUp() / 2,
                                                      Variables.getInstance().getClimbMaxSpeedDown() / 2);
        double modifyPitch = OI.getInstance().applyPID(OI.CLIMB_PITCH_SYSTEM, 
                                                       IMU.getInstance().getPitch(), 
                                                       0.0, 
                                                       Variables.getInstance().getClimbPitchkP(), 
                                                       Variables.getInstance().getClimbPitchkI(), 
                                                       Variables.getInstance().getClimbPitchkD(),
                                                       Variables.getInstance().getClimbMaxSpeedUp(),
                                                       Variables.getInstance().getClimbMaxSpeedDown());
        double speedLeft;
        double speedRight;

        // roll is positive clockwise

        if (ClimbRear.getInstance().getBothAdjustingBool()) { // if we want this to sync w the rear climb, takes priority over the frontclimb
            speed = OI.getInstance().applyPID(OI.CLIMB_POS_SYSTEM,
                                              ClimbRear.getInstance().getClimbRearPosition(),
                                              ClimbRear.getInstance().getTargetPosition(),
                                              Variables.getInstance().getClimbPoskP(),
                                              Variables.getInstance().getClimbPoskI(),
                                              Variables.getInstance().getClimbPoskD(),
                                              Variables.getInstance().getClimbMaxSpeedUp(),
                                              Variables.getInstance().getClimbMaxSpeedDown());
        } else if (adjusting) {
            speed = OI.getInstance().applyPID(OI.CLIMB_POS_SYSTEM,
                                              getClimbLeftPosition(),
                                              targetPosition,
                                              Variables.getInstance().getClimbPoskP(),
                                              Variables.getInstance().getClimbPoskI(),
                                              Variables.getInstance().getClimbPoskD(),
                                              Variables.getInstance().getClimbMaxSpeedUp(),
                                              Variables.getInstance().getClimbMaxSpeedDown());
        }

        if ((OI.getInstance().getOperatorClimbFront() && OI.getInstance().getOperatorClimbRear()) || ClimbRear.getInstance().getBothAdjustingBool()) {
            speed -= modifyPitch;
        }

        /*if (modifyRoll > 0) {
            speedLeft = speed;
            speedRight = speed - modifyRoll;
        } else {
            speedLeft = speed + modifyRoll;
            speedRight = speed;
        }*/
        speedLeft = speedRight = speed;

        if ((getClimbLeftPosition() > Variables.CLIMB_LEFT_SLOW_DOWN_MAX && speedLeft > 0)
            || (getClimbLeftPosition() < Variables.CLIMB_LEFT_SLOW_DOWN_MIN && speedLeft < 0)) {
            speedLeft *= 0.1;
        }

        if ((getClimbRightPosition() > Variables.CLIMB_RIGHT_SLOW_DOWN_MAX && speedRight > 0)
            || (getClimbRightPosition() < Variables.CLIMB_RIGHT_SLOW_DOWN_MIN && speedRight < 0)) {
            speedRight *= 0.1;
        }

        if (Sensors.getInstance().getClimbLeftLimit() && speedLeft < 0
            || leftSoftLimits(speedLeft)) {
            speedLeft = 0;
        }

        if (Sensors.getInstance().getClimbRightLimit() && speedRight < 0
            || rightSoftLimits(speedRight)) {
            speedRight = 0;
        }
    
        _climbFrontMotorLeft.set(speedLeft);
        _climbFrontMotorRight.set(speedRight);
    }

    public boolean leftSoftLimits(double speed) {
        if (getClimbLeftPosition() > Variables.CLIMB_LEFT_MAX && speed > 0) {
            return true;
        }
        if (getClimbLeftPosition() < 0 && speed < 0) {
            return true;
        }
        return false;
    }

    public boolean rightSoftLimits(double speed) {
        if (getClimbRightPosition() > Variables.CLIMB_RIGHT_MAX && speed > 0) {
            return true;
        }
        if (getClimbRightPosition() < 0 && speed < 0) {
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

        if ((getClimbLeftPosition() > Variables.CLIMB_LEFT_SLOW_DOWN_MAX && speedLeft > 0)
            || (getClimbLeftPosition() < Variables.CLIMB_LEFT_SLOW_DOWN_MIN && speedLeft < 0)) {
            speedLeft *= 0.1;
        }

        if ((getClimbRightPosition() > Variables.CLIMB_RIGHT_SLOW_DOWN_MAX && speedRight > 0)
            || (getClimbRightPosition() < Variables.CLIMB_RIGHT_SLOW_DOWN_MIN && speedRight < 0)) {
            speedRight *= 0.1;
        }

        if (Sensors.getInstance().getClimbLeftLimit() && speedLeft < 0
            || leftSoftLimits(speedLeft)) {
            speedLeft = 0;
        }

        if (Sensors.getInstance().getClimbRightLimit() && speedRight < 0
            || rightSoftLimits(speedRight)) {
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

    public void setTargetPosition(double target) {
        targetPosition = target;
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public void setAdjustingBool(boolean bool) {
        adjusting = bool;
    }

    public boolean getAdjustingBool() {
        return adjusting;
    }

}