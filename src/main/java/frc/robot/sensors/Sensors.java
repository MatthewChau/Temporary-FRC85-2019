/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Addresses;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.ClimbFront;
import frc.robot.subsystems.ClimbRear;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;

public class Sensors extends Subsystem {

    private static Sensors _instance = null;
    private DigitalInput _liftTopLimit, _liftCenterLimit, _liftBottomLimit, _liftFrontLimit, _liftRearLimit;

    private DigitalInput _intakeTopLimit;

    private DigitalInput _climbLeftLimit, _climbRightLimit, _climbRearLimit;
    Timer timer = new Timer();

    private Sensors() {
        _liftTopLimit = new DigitalInput(Addresses.LIFT_TOP_LIMIT);
        _liftCenterLimit = new DigitalInput(Addresses.LIFT_CENTER_LIMIT);
        _liftBottomLimit = new DigitalInput(Addresses.LIFT_BOTTOM_LIMIT);

        _liftFrontLimit= new DigitalInput(Addresses.LIFT_FRONT_LIMIT);
        _liftRearLimit = new DigitalInput(Addresses.LIFT_BACK_LIMIT);

        _intakeTopLimit = new DigitalInput(Addresses.INTAKE_TOP_LIMIT);

        _climbLeftLimit = new DigitalInput(Addresses.CLIMB_LEFT_LIMIT);
        _climbRightLimit = new DigitalInput(Addresses.CLIMB_RIGHT_LIMIT);
        _climbRearLimit = new DigitalInput(Addresses.CLIMB_REAR_LIMIT);
        startTimer();
    }

    public static Sensors getInstance() {
        if (_instance == null) {
            _instance = new Sensors();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void startTimer() {
        timer.start();
    }

    public void resetTimer() {
        timer.reset();
    }

    public void stopTimer() {
        timer.stop();
    }

    public boolean getLiftTopLimit() {
        return !_liftTopLimit.get();
    }

    public boolean getLiftCenterLimit() {
        return !_liftCenterLimit.get();
    }

    public boolean getLiftBottomLimit() {
        return !_liftBottomLimit.get();
    }

    public boolean getLiftFrontLimit() {
        return !_liftFrontLimit.get();
    }

    public boolean getLiftRearLimit() {
        return !_liftRearLimit.get();
    }

    public boolean getIntakeTopLimit() {
        return !_intakeTopLimit.get();
    }

    public boolean getClimbRightLimit() {
        return !_climbRightLimit.get();
    }

    public boolean getClimbLeftLimit() {
        return !_climbLeftLimit.get();
    }

    public boolean getClimbRearLimit() {
        return !_climbRearLimit.get();
    }

    public void checkSensorsForEncoderReset() {
        if (getLiftBottomLimit()) {
            Elevator.getInstance().setVerticalPosition(0);
        }

        if (getIntakeTopLimit() || SmartDashboard.getBoolean("Reset Wrist Encoder", false)) {
            Intake.getInstance().setWristPosition(0);
        }

        if (getLiftRearLimit()) {
            Mast.getInstance().setHorizontalPosition(0);
        }

        if (getClimbRightLimit()) {
            if (timer.get() > 1.0) {
                ClimbFront.getInstance().setClimbRightPosition(0);
            }
        } else {
            timer.reset();
        }

        if (getClimbLeftLimit()) {
            ClimbFront.getInstance().setClimbLeftPosition(0);
        }

        if (getClimbRearLimit()) {
            ClimbRear.getInstance().setClimbRearPosition(0);
        }
    }

}