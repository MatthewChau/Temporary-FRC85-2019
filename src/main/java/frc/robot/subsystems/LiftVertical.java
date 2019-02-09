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
import frc.robot.commands.lift.LiftVerticalWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


public class LiftVertical extends Subsystem {

    private static LiftVertical _instance = null;

    private TalonSRX _liftLeftMotor, _liftRightMotor;

    private double _verticalSpeed;
  
    private LiftVertical() {
        _liftLeftMotor = new TalonSRX(Addresses.LIFT_LEFT_MOTOR);
        _liftLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        _liftRightMotor = new TalonSRX(Addresses.LIFT_RIGHT_MOTOR);
    }

    public static LiftVertical getInstance() {
        if (_instance == null) {
            _instance = new LiftVertical();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new LiftVerticalWithJoystick());
    }

    public void verticalShift(double speed) {
        if ((ProxSensors.getInstance().getLiftTopLimit() && speed > 0) 
            || (ProxSensors.getInstance().getLiftBottomLimit() && speed < 0)) {
                _liftLeftMotor.set(ControlMode.PercentOutput, speed);
                _liftRightMotor.set(ControlMode.PercentOutput, -speed);
            } else {
                _liftLeftMotor.set(ControlMode.PercentOutput, 0);
                _liftRightMotor.set(ControlMode.PercentOutput, 0);
            }
    } 

    /**
     * @param targetPosition, encoder counts
     * @param speedMax, max speed that motor will run at 
     */
    public void verticalShift(int targetPosition, double speedMax) {
        double speed = OI.getInstance().applyPID(OI.getInstance().LIFT_VERTICAL_SYSTEM, getVerticalPosition(), targetPosition, 
            Variables.getInstance().getVerticalLiftKP(), Variables.getInstance().getVerticalLiftKI(), Variables.getInstance().getVerticalLiftKD(), 
            Math.abs(speedMax), -Math.abs(speedMax));

        _liftLeftMotor.set(ControlMode.PercentOutput, speed);
        _liftRightMotor.set(ControlMode.PercentOutput, -speed); //Guess, since the motors are gonna be facing different directions. 
    }

    public int getVerticalPosition() {
        return _liftLeftMotor.getSelectedSensorPosition();
    }
   
}
