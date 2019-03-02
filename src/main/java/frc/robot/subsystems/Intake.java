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
import frc.robot.commands.intake.IntakeWithJoystick;
import frc.robot.sensors.ProxSensors;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The intake subsystem
 */
public class Intake extends Subsystem {

    private static Intake _instance = null;

    private TalonSRX _flipper, _roller;

    private boolean adjusting;
    private double targetPos;

    private Intake() {
        _flipper = new TalonSRX(Addresses.INTAKE_FLIPPER);
        _flipper.setNeutralMode(NeutralMode.Brake);
        _flipper.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
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
        setDefaultCommand(new IntakeWithJoystick());
    }

    public void setFlipper(double speed) {
        if (adjusting) { // if we are adjusting
            speed = OI.getInstance().applyPID(OI.getInstance().INTAKE_SYSTEM, 
                                              getFlipperPosition(), 
                                              targetPos, 
                                              Variables.getInstance().getIntakeKP(), 
                                              Variables.getInstance().getIntakeKI(), 
                                              Variables.getInstance().getIntakeKD(), 
                                              Variables.getInstance().getMaxSpeedUpIntake(), 
                                              Variables.getInstance().getMaxSpeedDownIntake());
        } else if (speed > 0.0) {
            speed = 0.3;
            targetPos = getFlipperPosition();
        } else if (speed < 0.0) {
            speed = -0.3;
            targetPos = getFlipperPosition();
        } else {
            speed = 0.0;
        }

        if ((ProxSensors.getInstance().getIntakeBottomLimit() && speed > 0) // if we trying to exceed top limit
            || (ProxSensors.getInstance().getIntakeTopLimit() && speed < 0) // if we trying to exceed bottom limit
            || (!OI.getInstance().getOperatorIntakeRotate() && !adjusting)) { // if the button isn't pressed and we are not adjusting
            _flipper.set(ControlMode.PercentOutput, 0);
        } else {
            _flipper.set(ControlMode.PercentOutput, speed);
        }
    }

    /**
     * @param position in encoder counts
     */
    public void setFlipperPosition(int position) {
        _flipper.setSelectedSensorPosition(position);
    }

    public double getFlipperPosition() {
        return _flipper.getSelectedSensorPosition();
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

}
