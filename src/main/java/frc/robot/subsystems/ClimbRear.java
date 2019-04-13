package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class ClimbRear extends Subsystem {

    private static ClimbRear _instance = null;

    private CANSparkMax _climbRearMotor;

    private Servo _climbServo;

    private Timer _timer;

    private double _servoAngle;

    private double targetPosition;
    private boolean adjusting = false;
    private boolean bothAdjusting = false;

    private ClimbRear() {
        _climbRearMotor = new CANSparkMax(Addresses.CLIMB_REAR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        _climbRearMotor.setIdleMode(IdleMode.kBrake);
        //_climbRearMotor.setSmartCurrentLimit(0, 5700, 3000);

        _climbServo = new Servo(Addresses.CLIMB_SERVO);

        _timer = new Timer();
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
        if (adjusting || bothAdjusting) {
            speed = OI.getInstance().applyPID(OI.CLIMB_POS_SYSTEM,
                                              getClimbRearPosition(),
                                              targetPosition,
                                              Variables.getInstance().getClimbPoskP(),
                                              Variables.getInstance().getClimbPoskI(),
                                              Variables.getInstance().getClimbPoskD(),
                                              Variables.getInstance().getClimbMaxSpeedUp(),
                                              Variables.getInstance().getClimbMaxSpeedDown());
        }

        if ((getClimbRearPosition() < Variables.CLIMB_REAR_SLOW_DOWN_MIN && speed < 0)
            || (getClimbRearPosition() > Variables.CLIMB_REAR_SLOW_DOWN_MAX && speed > 0)) {
            speed *= 0.1;
        }

        if (getServo() == Variables.getInstance().getClimbLocked()
            || (Sensors.getInstance().getClimbRearLimit() && speed < 0)
            || softLimits(speed)) {
            speed = 0;
        }
        
        _climbRearMotor.set(speed);
    }

    public void setClimbRearMotor(double speed) {

        if ((getClimbRearPosition() < Variables.CLIMB_REAR_SLOW_DOWN_MIN && speed < 0)
            || (getClimbRearPosition() > Variables.CLIMB_REAR_SLOW_DOWN_MAX && speed > 0)) {
            speed *= 0.1;
        }

        if (getServo() == Variables.getInstance().getClimbLocked()
            || Sensors.getInstance().getClimbRearLimit() && speed < 0
            || softLimits(speed)
            || _timer.get() < 0.5) {
            speed = 0;
        }
        
        _climbRearMotor.set(speed);
    }

    private boolean softLimits(double speed) {
        if (getClimbRearPosition() > Variables.CLIMB_REAR_MAX && speed > 0) {
            return true;
        }
        /*if (getClimbRearPosition() < 0 && speed < 0) {
            return true;
        }*/
        return false;
    }

    public double getClimbRearPosition() {
        return _climbRearMotor.getEncoder().getPosition();
    }

    public void setClimbRearPosition(double position) {
        _climbRearMotor.setEncPosition(position);
    }

    public void setServo(double angle) {
        _climbServo.setAngle(angle);
        _servoAngle = angle;
    }

    public double getServo() {
        return _servoAngle;
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

    public void setBothAdjustingBool(boolean bool) { // set front adjusting bool to get the front to adjust with this
        bothAdjusting = bool;
    }

    public boolean getBothAdjustingBool() {
        return bothAdjusting;
    }

    public boolean getClimbInProgress() {
        //if (getClimbRearPosition() < Variables.CLIMB_REAR_SLOW_DOWN_MIN) {
            return false;
        //} else {
        //    return true;
        //}
    }

    public void startTimer() {
        _timer.start();
    }

    public void resetTimer() {
        _timer.reset();
    }

    public void stopTimer() {
        _timer.stop();
    }

}