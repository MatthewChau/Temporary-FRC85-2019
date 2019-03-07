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

    private double targetPos;

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
        if (adjusting || (OI.getInstance().getOperatorLiftVertical() && speed == 0.0)) {
            speed = OI.getInstance().applyPID(OI.getInstance().ELEVATOR_SYSTEM, 
                                              getVerticalPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getElevatorKP(), 
                                              Variables.getInstance().getElevatorKI(), 
                                              Variables.getInstance().getElevatorKD(), 
                                              0.5, 
                                              -0.2);
        } else if (speed > Variables.getInstance().DEADBAND_OPERATORSTICK) {
            speed = 0.5;
            targetPos = getVerticalPosition();
        } else if (speed < -Variables.getInstance().DEADBAND_OPERATORSTICK) {
            speed = -0.2;
            targetPos = getVerticalPosition();
        }

        if ((ProxSensors.getInstance().getLiftTopLimit() && speed > 0.0)
             || (ProxSensors.getInstance().getLiftBottomLimit() && speed < 0.0)
             || (softLimits(speed) && !SmartDashboard.getBoolean("Disable Elevator Soft Limits", false))) {
            speed = 0.0;
        }

        if (ProxSensors.getInstance().getLiftBottomLimit()) {
            setVerticalPosition(0);
        }

        if (Math.abs(getServo() - Variables.getInstance().getElevatorLocked()) < 30) { // doesn't work
            speed = 0;
        }

        _liftLeftMotor.set(ControlMode.PercentOutput, speed);
        _liftRightMotor.set(ControlMode.PercentOutput, speed);
    }

    private boolean softLimits(double speed) {
        double mastPosition = Mast.getInstance().getHorizontalPosition();
        double verticalPosition = getVerticalPosition();
        double intakePosition = Intake.getInstance().getWristPosition();

        //NEW: Enforce positional limits 
        //max is always the same for the elevator
        if(verticalPosition > Variables.ELEVATOR_MAX_POS // top limit
            && speed > 0) 
        {
            return true;
        }
        if(mastPosition < Variables.MAST_ELEVATOR_BREAKPOINT // bottom limit back
            && verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_PROTECTED
            && speed < 0)
        {
            return true;
        }
        if(mastPosition > Variables.MAST_ELEVATOR_BREAKPOINT // bottom limit front
            && intakePosition <= Variables.WRIST_ELEVATOR_BREAKPOINT // would this be necessary?
            && verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO
            && speed < 0)
        {
            return true;
        }
        if(mastPosition > Variables.MAST_ELEVATOR_BREAKPOINT
            && intakePosition >= Variables.WRIST_ELEVATOR_BREAKPOINT
            && verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH
            && speed < 0)
        {
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

    //NEW: (... and untested!)  Enforce positional limits
    // BROOKE AND SCOTT WERE also HERE, HIIIIIIIIIIIIIII
    // commented out some of the logic so that we don't jump to limits automatically
    // may need further testing...
    public void setTargetPosition(double target) {
        //max is always the same for the elevator
        if(target > Variables.ELEVATOR_MAX_POS) {
            target = Variables.ELEVATOR_MAX_POS;
        }
        //if mast is protected, use a special minimum value for the elevator
        else if(Mast.getInstance().getHorizontalPosition() < Variables.MAST_ELEVATOR_BREAKPOINT){
            /*if(target < Variables.ELEVATOR_MIN_POS_MAST_PROTECTED)
                target = Variables.ELEVATOR_MIN_POS_MAST_PROTECTED;*/
        }
        //otherwise, minimum value is determined by the wrist
        else{
            if(Intake.getInstance().getWristPosition() > Variables.WRIST_ELEVATOR_BREAKPOINT) {
                /*if(target < Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH) 
                    target = Variables.ELEVATOR_MIN_POS_MAST_FORWARD_HATCH;*/
            }
            else {
                /*if(target < Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO) 
                    target = Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO;*/
            }
        }
        targetPos = target;
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
    }

    public double getServo() {
        return _liftServo.getAngle();
    }

    public TalonSRX getIMUTalon() {
        return _liftLeftMotor;
    }
   
}
