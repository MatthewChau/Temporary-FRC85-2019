
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
 * 3 Encoders (1 for slide)
 * 2 775s (TalonSRX) for lift up/down
 * 4 Proximity sensors (detect prescences of something)
 * 1 CIM (TalonSRX) for slide
 * 2 motors on lift for lift, 1 CIM for 7-inch shift
 */
public class Lift extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static Lift _instance = null;

  // Vertical
  private TalonSRX _liftLeft, _liftRight; 
  // Horizontal
  private TalonSRX _cim; 

  private double _verticalSpeed;
  private double _horizontalSpeed;

  // Move to variables class later
  private int[] phaseValues = {0, 3000, 6000, 9000}; //get phase based on index
  private int[] horizontalValues = {0, 3000}; //get preset values based on index

    public Lift() {
        _liftLeft = new TalonSRX(Addresses.LIFT_LEFT_MOTOR);
        _liftLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _liftRight = new TalonSRX(Addresses.LIFT_RIGHT_MOTOR);

        _cim = new TalonSRX(Addresses.LIFT_CIM_MOTOR);
        _cim.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    public static Lift getInstance() {
        if (_instance == null) {
            _instance = new Lift();
        }
        return _instance;
    }
  
    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LiftWithJoystick());
    }

    public void verticalShift(double speed) {
        _liftLeft.set(ControlMode.PercentOutput, speed);
        _liftRight.set(ControlMode.PercentOutput, -speed); //Since the motors are gonna be facing different directions. If going wrong direction, make the top line -speed and the bottom one speed.
    }

    public void horizontalShift(double speed) {
        _cim.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Checks the lift's proximity sensors, a false = do not drive
     */
    public boolean checkVerticalLift(double speed) {
        if ((ProxSensors.getInstance().getTopLimit() && speed > 0) || (ProxSensors.getInstance().getBottomLimit() && speed < 0)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkHorizontalLift(double speed) {
        if ((ProxSensors.getInstance().getLeftLimit() && speed > 0) || (ProxSensors.getInstance().getRightlimit() && speed < 0)) {
            return false;
        } else {
            return true;
        }
    }

    public double getLeftEncoderValue() {
        return _liftLeft.getSelectedSensorPosition();
    }

    public double getCimEncoderValue() {
        return _cim.getSelectedSensorPosition();
    }

    // Move to variables class
    public int getPhaseValue(int phase) {
        return phaseValues[phase];
    }

    public int getHorizontalPhase(int phase) {
        return horizontalValues[phase];
    }

}


