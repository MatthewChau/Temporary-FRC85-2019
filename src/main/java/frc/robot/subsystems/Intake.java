/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.sensors.ProxSensors;
import frc.robot.commands.intake.WristWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Intake extends Subsystem {

    private static Intake _instance = null;

    private TalonSRX _wrist, _roller;

    private boolean adjusting;
    private double targetPos;

    private Intake() {
        _wrist = new TalonSRX(Addresses.INTAKE_WRIST);
        _wrist.setNeutralMode(NeutralMode.Brake);
        _wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _roller = new TalonSRX(Addresses.INTAKE_ROLLER);
        _roller.setNeutralMode(NeutralMode.Brake);
    }

    public static Intake getInstance() {
        if (_instance == null) {
            _instance = new Intake();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void setWrist(double speed) {
        if (adjusting) { // if we are adjusting
            speed = OI.getInstance().applyPID(OI.INTAKE_SYSTEM, 
                                              getWristPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getWristKP(), 
                                              Variables.getInstance().getWristKI(), 
                                              Variables.getInstance().getWristKD(), 
                                              Variables.getInstance().getMaxSpeedUpIntake(), 
                                              Variables.getInstance().getMaxSpeedDownIntake());
        } else if (speed > 0.0) {
            speed *= 0.6;
            targetPos = getWristPosition();
        } else if (speed < 0.0) {
            speed *= 0.6;
            targetPos = getWristPosition();
        } else {
            speed = 0.0;
        }

        if ((ProxSensors.getInstance().getIntakeTopLimit() && !SmartDashboard.getBoolean("Disable Intake Prox Limit", true) && speed > 0) // if we trying to exceed top limit
            || (!OI.getInstance().getOperatorWristRotate() && !adjusting) // if the button isn't pressed and we are not adjusting
            || (softLimits(speed) && !SmartDashboard.getBoolean("Disable Intake Soft Limits", false))) {
            _wrist.set(ControlMode.PercentOutput, 0);
        } else {
            _wrist.set(ControlMode.PercentOutput, speed);
        }

        SmartDashboard.putBoolean("Wrist Soft Limits Activated", softLimits(speed));
    }

    private boolean softLimits(double speed) {
        double verticalPosition = Elevator.getInstance().getVerticalPosition();
        double intakePosition = getWristPosition();
        double mastPosition = Mast.getInstance().getHorizontalPosition();

        if (!SmartDashboard.getBoolean("Disable Intake Top Limit", false) // top limit
            && intakePosition >= Variables.WRIST_MAX_POS
            && speed > 0) {
            return true;
        } else if (mastPosition < Variables.MAST_BREAKPOINT // bottom limit when mast is back
                   && intakePosition <= Variables.WRIST_MIN_POS_MAST_BACK
                   && verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_PROTECTED
                   && speed < 0) {
            return true;
        } else if (verticalPosition >= Variables.ELEVATOR_MIN_POS_FOR_WRIST_LIFT_HIGH // bottom limit when elevator is higher than a certain point
                    && intakePosition <= Variables.WRIST_90
                    && speed < 0) {
            return true;
        } else if (intakePosition <= Variables.WRIST_MIN_POS // general bottom limit
                   && speed < 0) {
            return true;
        }
        return false;
    }

    public void setWristMotor(double speed) {
        _wrist.set(ControlMode.PercentOutput, speed);
    }

    /**
     * @param position in encoder counts
     */
    public void setWristPosition(int position) {
        _wrist.setSelectedSensorPosition(position);
    }

    public int getWristPosition() {
        return _wrist.getSelectedSensorPosition();
    }

    public void setRoller(double speed) {
        _roller.set(ControlMode.PercentOutput, -speed);
    }

    public void setTargetPos(double target) {
        targetPos = target;
    }

    public void changeAdjustingBool(boolean bool) {
        adjusting = bool;
    }

    public boolean getAdjustingBool() {
        return adjusting;
    }

    public double getWristMotorVoltage() {
        return _wrist.getMotorOutputVoltage();
    }

    public double getRollerMotorVoltage() {
        return _roller.getMotorOutputVoltage();
    }

    public double getWristMotorCurrent() {
        return _wrist.getOutputCurrent();
    }

    public double getRollerMotorCurrent() {
        return _roller.getOutputCurrent();
    }

}
