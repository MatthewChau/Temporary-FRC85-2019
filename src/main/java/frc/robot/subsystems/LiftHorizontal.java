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
import frc.robot.commands.lift.LiftHorizontalWithJoystick;
import frc.robot.sensors.ProxSensors;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class LiftHorizontal extends Subsystem {

    private static LiftHorizontal _instance = null;

    private TalonSRX _liftRearMotor;

    private double targetPos;
    private boolean adjusting;

    private LiftHorizontal() {
        _liftRearMotor = new TalonSRX(Addresses.LIFT_CIM_MOTOR);
        _liftRearMotor.setNeutralMode(NeutralMode.Brake);
        _liftRearMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    }

    public static LiftHorizontal getInstance() {
        if (_instance == null) {
            _instance = new LiftHorizontal();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new LiftHorizontalWithJoystick());
    }

    public void horizontalShift(double speed) {
        if (adjusting && !OI.getInstance().getOperatorLiftHorizontal()) {
            speed = OI.getInstance().applyPID(OI.getInstance().LIFT_HORIZONTAL_SYSTEM, 
                                              getHorizontalPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getHorizontalLiftKP(), 
                                              Variables.getInstance().getHorizontalLiftKI(), 
                                              Variables.getInstance().getHorizontalLiftKD(), 
                                              0.5, 
                                              -0.5);
        } else if (speed > 0.0) {
            speed = 0.5;
        } else if (speed < 0.0) {
            speed = -0.5;
        } else {
            speed = 0.0;
        }
        
        if ((ProxSensors.getInstance().getLiftFrontLimit() && speed > 0.0)
            || (ProxSensors.getInstance().getLiftRearLimit() && speed < 0.0)
            || (!OI.getInstance().getOperatorLiftHorizontal() && !adjusting)
            || softLimits(speed)) {
            _liftRearMotor.set(ControlMode.PercentOutput, 0);
        } else {
            _liftRearMotor.set(ControlMode.PercentOutput, speed);
        }

        if (ProxSensors.getInstance().getLiftRearLimit()) {
            setHorizontalPosition(0);
        }
    }

    private boolean softLimits(double speed) {
        double mastPosition = getHorizontalPosition();
        double verticalPosition = LiftVertical.getInstance().getVerticalPosition();
        double intakePosition = Intake.getInstance().getFlipperPosition();

        if (verticalPosition < Variables.getInstance().CARGO_FLOOR
            && intakePosition < OI.getInstance().convertDegreesToIntake(10)
            && mastPosition < Variables.getInstance().MAST_PROTECTED
            && speed < 0.0) {
            return true;
        }
        return false;
    }

    public void setHorizontalPosition(int position) {
        _liftRearMotor.setSelectedSensorPosition(position);
    }

    public int getHorizontalPosition() {
        return _liftRearMotor.getSelectedSensorPosition();
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