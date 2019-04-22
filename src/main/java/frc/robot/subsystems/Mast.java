/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.Addresses;
import frc.robot.sensors.Sensors;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Mast extends Subsystem {

    private static Mast _instance = null;

    private TalonSRX _mastMotor;

    private double targetPos;
    private boolean adjusting;

    private Mast() {
        _mastMotor = new TalonSRX(Addresses.LIFT_CIM_MOTOR);
        _mastMotor.setNeutralMode(NeutralMode.Brake);
        _mastMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        _mastMotor.configOpenloopRamp(0.5);
    }

    public static Mast getInstance() {
        if (_instance == null) {
            _instance = new Mast();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void horizontalShift(double speed) {
        if (adjusting && !OI.getInstance().getMastJoystickButton()) {
            speed = OI.getInstance().applyPID(OI.MAST_SYSTEM, 
                                              getMastPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getMastKP(), 
                                              Variables.getInstance().getMastKI(), 
                                              Variables.getInstance().getMastKD(), 
                                              0.8, 
                                              -0.8);
        } else if (speed > 0.0) {
            speed *= 0.8;
            setTargetPosition(getMastPosition());
        } else if (speed < 0.0) {
            speed *= 0.8;
            setTargetPosition(getMastPosition());
        } else {
            speed = 0.0;
        }

        if ((Sensors.getInstance().getLiftFrontLimit() && speed > 0.0)
            || (Sensors.getInstance().getLiftRearLimit() && speed < 0.0)
            || (!OI.getInstance().getMastJoystickButton() && !adjusting)
            || (softLimits(speed) && !SmartDashboard.getBoolean("Disable Mast Soft Limits", false))) {
            _mastMotor.set(ControlMode.PercentOutput, 0);
        } else {
            _mastMotor.set(ControlMode.PercentOutput, speed);
        }

        SmartDashboard.putBoolean("Mast Soft Limits Activated", softLimits(speed));
    }

    public void setMastMotor(double speed) {
        _mastMotor.set(ControlMode.PercentOutput, speed);
        setTargetPosition(getMastPosition());
    }

    private boolean softLimits(double speed) {
        double mastPosition = getMastPosition();
        double verticalPosition = Elevator.getInstance().getElevatorPosition();
        double intakePosition = Intake.getInstance().getWristPosition();

        // mast limits need a front limit, a rear limit, & a thing if both wrist & elevator are low 

        if (mastPosition >= Variables.MAST_MAX_POS // front limit
            && speed > 0) {
            return true;
        } else if (verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_PROTECTED // can't move back if both wrist and elevator are low enough
                   && intakePosition <= Variables.WRIST_MIN_POS_MAST_BACK
                   //&& mastPosition <= Variables.MAST_BREAKPOINT
                   && speed < 0) {
            return true;
        } else if (mastPosition <= Variables.MAST_MIN_POS // rear limit
                   && speed < 0) {
            return true;
        }
        return false;
    }

    public void setHorizontalPosition(int position) {
        _mastMotor.setSelectedSensorPosition(position);
    }

    public int getMastPosition() {
        return _mastMotor.getSelectedSensorPosition();
    }

    public double getMastMotorVoltage() {
        return _mastMotor.getMotorOutputVoltage();
    }

    public double getMastMotorCurrent() {
        return _mastMotor.getOutputCurrent();
    }

    public void setTargetPosition(double target) {
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

}