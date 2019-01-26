/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import frc.robot.sensors.ProxSensors;
import frc.robot.commands.lift.LiftWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * 3 Encoders (1 for slide) 2 775s (TalonSRX) for lift up/down 4 Proximity
 * sensors (detect prescences of something) 1 CIM (TalonSRX) for slide 2 motors
 * on lift for lift, 1 CIM for 7-inch shift
 */
public class LiftHorizontal extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private static LiftHorizontal _instance = null;

    // Horizontal Motor
    private TalonSRX _liftRearMotor;

    private double _horizontalSpeed;

    // Move to variables class later
    private int[] phaseValues = { 0, 3000, 6000, 9000 }; // get phase based on index
    private int[] horizontalValues = { 0, 3000 }; // get preset values based on index

    private LiftHorizontal() {

        _liftRearMotor = new TalonSRX(Addresses.LIFT_CIM_MOTOR);
        _liftRearMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    public static LiftHorizontal getInstance() {
        if (_instance == null) {
            _instance = new LiftHorizontal();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LiftWithJoystick());
    }

    public void horizontalShift(double speed) {
        _liftRearMotor.set(ControlMode.PercentOutput, speed);
    }

    public void horizontalShift(int position, double speed) {
        if (position > getHorizontalPosition()) {

        } else if (position > getHorizontalPosition()) {

        }

        _liftRearMotor.set(ControlMode.PercentOutput, speed);
    }

    public boolean checkHorizontalLift(double speed) {
        if ((ProxSensors.getInstance().getLiftFrontLimit() && speed > 0)
                || (ProxSensors.getInstance().getLiftRearLimit() && speed < 0)) {
            return false;
        } else {
            return true;
        }
    }

    public int getHorizontalPosition() {
        return _liftRearMotor.getSelectedSensorPosition();
    }

    // Move to variables class
    public int getPhaseValue(int phase) {
        return phaseValues[phase];
    }

    public int getHorizontalPhase(int phase) {
        return horizontalValues[phase];
    }

}