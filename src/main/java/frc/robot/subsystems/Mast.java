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
import frc.robot.commands.lift.MastWithJoystick;
import frc.robot.sensors.ProxSensors;

import edu.wpi.first.wpilibj.command.Subsystem;

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
        _mastMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    }

    public static Mast getInstance() {
        if (_instance == null) {
            _instance = new Mast();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new MastWithJoystick());
    }

    public void horizontalShift(double speed) {
        if (adjusting && !OI.getInstance().getOperatorLiftHorizontal()) {
            speed = OI.getInstance().applyPID(OI.getInstance().MAST_SYSTEM, 
                                              getHorizontalPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getMastKP(), 
                                              Variables.getInstance().getMastKI(), 
                                              Variables.getInstance().getMastKD(), 
                                              0.5, 
                                              -0.5);
        } else if (speed > 0.0) {
            speed = 0.5;
            setTargetPosition(getHorizontalPosition());
        } else if (speed < 0.0) {
            speed = -0.5;
            setTargetPosition(getHorizontalPosition());
        } else {
            speed = 0.0;
        }
        
        

        if ((ProxSensors.getInstance().getLiftFrontLimit() && speed > 0.0)
            || (ProxSensors.getInstance().getLiftRearLimit() && speed < 0.0)
            || (!OI.getInstance().getOperatorLiftHorizontal() && !adjusting)
            || softLimits(speed)) {
            _mastMotor.set(ControlMode.PercentOutput, 0);
        } else {
            _mastMotor.set(ControlMode.PercentOutput, speed);
        }

        if (ProxSensors.getInstance().getLiftRearLimit()) {
            setHorizontalPosition(0);
        }
    }

    public void setMastMotor(double speed) {
        _mastMotor.set(ControlMode.PercentOutput, speed);
        setTargetPosition(getHorizontalPosition());
    }

    private boolean softLimits(double speed) {
        double mastPosition = getHorizontalPosition();
        double verticalPosition = Elevator.getInstance().getVerticalPosition();
        
        //NEW: use the limits we defined to prevent manually driving the mast unsafely
        if (verticalPosition > Variables.ELEVATOR_MIN_POS_MAST_PROTECTED 
            &&  mastPosition >= Variables.MAST_MAX_POS 
            && speed > 0)
        {
            return true;
        }
        if (verticalPosition > Variables.ELEVATOR_MIN_POS_MAST_PROTECTED 
            &&  mastPosition <= Variables.MAST_MIN_POS 
            && speed < 0) 
        {
            return true;
        }
        if (verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_PROTECTED 
            &&  mastPosition >= Variables.MAST_MAX_POS 
            && speed > 0)
        {
            return true;
        }
        if (verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_PROTECTED 
            &&  mastPosition <= Variables.MAST_ELEVATOR_BREAKPOINT 
            && speed < 0) 
        {
            return true;
        }

        return false;
    }

    public void setHorizontalPosition(int position) {
        
        _mastMotor.setSelectedSensorPosition(position);
    }

    public int getHorizontalPosition() {
        return _mastMotor.getSelectedSensorPosition();
    }

    public void setTargetPosition(double target) {
        //NEW: (... and untested!) If you assign a position beyond the max/min, set it to the max/min instead
        // BROOKE AND SCOTT WERE also HERE, HIIIIIIIIIIIIIII
        // commented out some of the logic so that we don't jump to limits automatically
        // may need further testing...
        if (Elevator.getInstance().getVerticalPosition() > Variables.ELEVATOR_MIN_POS_MAST_PROTECTED)
        {
            /*if (target < Variables.MAST_MIN_POS)
                target = Variables.MAST_MIN_POS;*/
            if (target > Variables.MAST_MAX_POS)
                target = Variables.MAST_MAX_POS;
            }
        else {
            //If the elevator is down, we have a different minimum value for the mast
            /*if (target < Variables.MAST_ELEVATOR_BREAKPOINT)
                target = Variables.MAST_ELEVATOR_BREAKPOINT;*/
            if (target > Variables.MAST_MAX_POS)
                target = Variables.MAST_MAX_POS;
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

}