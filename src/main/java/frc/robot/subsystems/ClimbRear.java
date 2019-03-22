package frc.robot.subsystems;
   
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.IMU;
import frc.robot.sensors.Sensors;
import frc.robot.commands.climb.ClimbRearLock;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;

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

    private Servo _climbServo;

    private double _servoAngle;

    private double targetPosition;
    private boolean adjusting = false;
    private boolean bothAdjusting = false;

    private ClimbRear() {
        _climbRearMotor = new CANSparkMax(Addresses.CLIMB_REAR_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        _climbRearMotor.setIdleMode(IdleMode.kBrake);
        //_climbRearMotor.setSmartCurrentLimit(0, 5700, 3000);
        _climbRearDriveMotor = new TalonSRX(Addresses.CLIMB_REAR_DRIVE);
        _climbRearDriveMotor.setNeutralMode(NeutralMode.Brake);
        _climbRearDriveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        _climbServo = new Servo(Addresses.CLIMB_SERVO);
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
            || (getClimbRearPosition() > Variables.CLIMB_LEFT_SLOW_DOWN_MAX && speed > 0)) {
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
            || (getClimbRearPosition() > Variables.CLIMB_LEFT_SLOW_DOWN_MAX && speed > 0)) {
            speed *= 0.1;
        }

        if (getServo() == Variables.getInstance().getClimbLocked()
            || Sensors.getInstance().getClimbRearLimit() && speed < 0
            || softLimits(speed)) {
            speed = 0;
        }
        
        _climbRearMotor.set(speed);
    }

    private boolean softLimits(double speed) {
        if (getClimbRearPosition() > Variables.CLIMB_REAR_MAX && speed > 0) {
            return true;
        }
        if (getClimbRearPosition() < 0 && speed < 0) {
            return true;
        }
        return false;
    }

    public double getClimbRearPosition() {
        return _climbRearMotor.getEncoder().getPosition();
    }

    public void setClimbRearPosition(double position) {
        _climbRearMotor.setEncPosition(position);
    }

    // CLIMB DRIVE MOTOR

    public void moveClimbRearDrive(double speed) {
        // decide that pid exists here maybe?
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

}