/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.OI;
import frc.robot.Addresses;
import frc.robot.Variables;
import frc.robot.sensors.ProxSensors;

import frc.robot.commands.lift.ElevatorLock;
import frc.robot.commands.lift.ElevatorWithJoystick;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Elevator extends Subsystem {

    private static Elevator _instance = null;

    private TalonSRX _liftLeftMotor, _liftRightMotor;

    private Servo _liftServo;

    private double targetPos, _servoAngle;

    private boolean adjusting;
  
    private Elevator() {
        _liftLeftMotor = new TalonSRX(Addresses.LIFT_LEFT_MOTOR);
        _liftLeftMotor.setNeutralMode(NeutralMode.Brake);
        _liftRightMotor = new TalonSRX(Addresses.LIFT_RIGHT_MOTOR);
        _liftRightMotor.setNeutralMode(NeutralMode.Brake);
        _liftRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        _liftServo = new Servo(Addresses.LIFT_SERVO);
    }

    public static Elevator getInstance() {
        if (_instance == null) {
            _instance = new Elevator();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ElevatorLock());
    }

    public void verticalShift(double speed) {
        if (adjusting
            || speed == 0.0
            || (softLimits(speed) && !SmartDashboard.getBoolean("Disable Elevator Soft Limits", false))) {
            speed = OI.getInstance().applyPID(OI.ELEVATOR_SYSTEM, 
                                              getVerticalPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getElevatorKP(), 
                                              Variables.getInstance().getElevatorKI(), 
                                              Variables.getInstance().getElevatorKD(), 
                                              0.6, 
                                              -0.3);
        } else if (speed > 0) {
            speed = 0.5;// * OI.getInstance().getOpStickModifier();
            targetPos = getVerticalPosition();
        } else if (speed < 0) {
            speed = -0.2;// * OI.getInstance().getOpStickModifier();
            targetPos = getVerticalPosition();
        }

        if ((ProxSensors.getInstance().getLiftTopLimit() && speed > 0.0)
             || (ProxSensors.getInstance().getLiftBottomLimit() && speed < 0.0)) {
            speed = 0.0;
        }

        if (getServo() == Variables.getInstance().getElevatorLocked()) {
            speed = 0.0;
        }

        if (ProxSensors.getInstance().getLiftBottomLimit()) {
            setVerticalPosition(0);
        }

        _liftLeftMotor.set(ControlMode.PercentOutput, speed);
        _liftRightMotor.set(ControlMode.PercentOutput, speed);
    }

    private boolean softLimits(double speed) {
        double mastPosition = Mast.getInstance().getHorizontalPosition();
        double verticalPosition = getVerticalPosition();
        double intakePosition = Intake.getInstance().getWristPosition();

        // lift needs a top limit, a bottom limit,
        // if the wrist is down and the mast is back
        // if the wrist is down and the mast is forward
        if (verticalPosition > Variables.ELEVATOR_MAX_POS // top limit
            && speed > 0) {
            setTargetPosition(Variables.ELEVATOR_MAX_POS);
            return true;
        } else if (mastPosition < Variables.MAST_BREAKPOINT // mast is back & wrist is down
                   && intakePosition < Variables.WRIST_MIN_POS_MAST_BACK
                   && verticalPosition < Variables.ELEVATOR_MIN_POS_MAST_PROTECTED
                   && speed < 0) {
            setTargetPosition(Variables.ELEVATOR_MIN_POS_MAST_PROTECTED);
            return true;
        } else if (mastPosition >= Variables.MAST_BREAKPOINT // mast is forward & wrist is down
                   && intakePosition < Variables.WRIST_MIN_POS_MAST_BACK
                   && verticalPosition < Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO
                   && speed < 0) {
            setTargetPosition(Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO);
            return true;
        } else if (verticalPosition < Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH // general bottom limit
                   && speed < 0) {
            setTargetPosition(Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH);
            return true;
        }
        return false;
    }

    public void setElevatorMotors(double speed) {
        _liftLeftMotor.set(ControlMode.PercentOutput, speed);
        _liftRightMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopMotors() {
        _liftLeftMotor.set(ControlMode.PercentOutput, 0.0);
        _liftRightMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void setVerticalPosition(int position) {
        _liftRightMotor.setSelectedSensorPosition(position);
    }

    public int getVerticalPosition() {
        return _liftRightMotor.getSelectedSensorPosition();
    }

    public double getElevatorLeftVoltage() {
        return _liftLeftMotor.getMotorOutputVoltage();
    }

    public double getElevatorRightVoltage() {
        return _liftRightMotor.getMotorOutputVoltage();
    }

    public double getElevatorLeftCurrent() {
        return _liftLeftMotor.getOutputCurrent();
    }

    public double getElevatorRightCurrent() {
        return _liftRightMotor.getOutputCurrent();
    }

    public void setTargetPosition(double target) {
        //max is always the same for the elevator
        //if(target > Variables.ELEVATOR_MAX_POS) {
        //    target = Variables.ELEVATOR_MAX_POS;
        //}
        //if mast is protected, use a special minimum value for the elevator
        /*else if(Mast.getInstance().getHorizontalPosition() < Variables.MAST_BREAKPOINT){
            if(target < Variables.ELEVATOR_MIN_POS_MAST_PROTECTED)
                target = Variables.ELEVATOR_MIN_POS_MAST_PROTECTED;
        }
        //otherwise, minimum value is determined by the wrist
        else{
            if(Intake.getInstance().getWristPosition() > Variables.WRIST_MIN_POS_MAST_BACK) {
                if(target < Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH) 
                    target = Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH;
            }
            else {
                if(target < Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO) 
                    target = Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO;
            }
        } else {*/
            targetPos = target;
        //}
    }

    public double getTargetPosition() {
        return targetPos;
    }

    public void changeAdjustingBool(boolean bool) {
        adjusting = bool;
    }

    public boolean getAdjustingBool() {
        return adjusting;
    }

    public void setServo(double degree) {
        _liftServo.setAngle(degree);
        _servoAngle = degree;
    }

    public double getServo() {
        return _servoAngle;
    }

    public TalonSRX getIMUTalon() {
        return _liftLeftMotor;
    }
   
}
