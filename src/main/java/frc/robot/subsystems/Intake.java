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
import frc.robot.commands.intake.WristWithJoystick;
import frc.robot.sensors.ProxSensors;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The intake subsystem
 */
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
        setDefaultCommand(new WristWithJoystick());
    }

    public void setWrist(double speed) {
        if (adjusting) { // if we are adjusting
            speed = OI.getInstance().applyPID(OI.getInstance().INTAKE_SYSTEM, 
                                              getWristPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getWristKP(), 
                                              Variables.getInstance().getWristKI(), 
                                              Variables.getInstance().getWristKD(), 
                                              Variables.getInstance().getMaxSpeedUpIntake(), 
                                              Variables.getInstance().getMaxSpeedDownIntake());
        } else if (speed > 0.0) {
            speed = 0.6;
            targetPos = getWristPosition();
        } else if (speed < 0.0) {
            speed = -0.6;
            targetPos = getWristPosition();
        } else {
            speed = 0.0;
        }

        if ((ProxSensors.getInstance().getIntakeBottomLimit() && speed > 0) // if we trying to exceed top limit
            || (ProxSensors.getInstance().getIntakeTopLimit() && speed < 0) // if we trying to exceed bottom limit
            || (!OI.getInstance().getOperatorIntakeRotate() && !adjusting) // if the button isn't pressed and we are not adjusting
            || softLimits(speed)) {
            _wrist.set(ControlMode.PercentOutput, 0);
        } else {
            _wrist.set(ControlMode.PercentOutput, speed);
        }
    }

    private boolean softLimits(double speed) {
        double mastPosition = Mast.getInstance().getHorizontalPosition();
        double verticalPosition = Elevator.getInstance().getVerticalPosition();
        double intakePosition = getWristPosition();

        /*if ((verticalPosition < Variables.getInstance().CARGO_FLOOR
            || (mastPosition < Variables.getInstance().MAST_PROTECTED && verticalPosition < Variables.getInstance().LIFT_MIN_FOR_MAST))
            && intakePosition < OI.getInstance().convertDegreesToIntake(10)
            && speed < 0.0) {
            return true;
        } else if (intakePosition > 0.0 && speed > 0.0 && !SmartDashboard.getBoolean("sMASH ME DADDY", false)) {
            return true;
        } else if (intakePosition < OI.getInstance().convertDegreesToIntake(100) && speed < 0.0) {
            return true;
        }
        return false;*/

        //NEW: Enforce positional limits
        if(!SmartDashboard.getBoolean("sMASH ME DADDY", false)
            && intakePosition >= Variables.WRIST_MAX_POS
            && speed < 0) 
        {
            return true;
        }
        if(verticalPosition > Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO
            && intakePosition <= Variables.WRIST_MIN_POS
            && speed > 0) 
        {
            return true;
        }
        if(verticalPosition <= Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO
            && intakePosition <= Variables.WRIST_ELEVATOR_BREAKPOINT
            && speed > 0) 
        {
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

    //NEW: (... and untested!)  Enforce positional limits
    public void setTargetPos(double target) {
        if(Elevator.getInstance().getVerticalPosition() > Variables.ELEVATOR_MIN_POS_MAST_FORWARD_CARGO) {
            if( target > Variables.WRIST_MAX_POS)
                target = Variables.WRIST_MAX_POS;
            if(target < Variables.WRIST_MIN_POS)
                target = Variables.WRIST_MIN_POS;
        }
        else {
            if(target < Variables.WRIST_ELEVATOR_BREAKPOINT)
                target = Variables.WRIST_ELEVATOR_BREAKPOINT;
            if(target > Variables.WRIST_MAX_POS)
                target = Variables.WRIST_MAX_POS;
        }
        targetPos = target;
    }

    public void changeAdjustingBool(boolean bool) {
        adjusting = bool;
    }

    public boolean getAdjustingBool() {
        return adjusting;
    }

}
