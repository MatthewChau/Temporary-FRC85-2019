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
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Intake;

public class ProxSensors extends Subsystem {

    private static ProxSensors _instance = null;
    private DigitalInput _liftTopLimit, _liftCenterLimit, _liftBottomLimit, _liftFrontLimit, _liftRearLimit;

    private DigitalInput _intakeTopLimit;

    private ProxSensors() {
        _liftTopLimit = new DigitalInput(Addresses.LIFT_TOP_LIMIT);
        _liftCenterLimit = new DigitalInput(Addresses.LIFT_CENTER_LIMIT);
        _liftBottomLimit = new DigitalInput(Addresses.LIFT_BOTTOM_LIMIT);

        _liftFrontLimit= new DigitalInput(Addresses.LIFT_FRONT_LIMIT);
        _liftRearLimit = new DigitalInput(Addresses.LIFT_BACK_LIMIT);

        _intakeTopLimit = new DigitalInput(Addresses.INTAKE_TOP_LIMIT);
    }

    public static ProxSensors getInstance() {
        if (_instance == null) {
            _instance = new ProxSensors();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
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

    public void checkProxForEncoderReset() {
        if (getLiftBottomLimit()) {
            Elevator.getInstance().setVerticalPosition(0);
        }

        if (getIntakeTopLimit() || SmartDashboard.getBoolean("Reset Wrist Encoder", false)) {
            Intake.getInstance().setWristPosition(0);
        }

        if (getLiftRearLimit()) {
            Mast.getInstance().setHorizontalPosition(0);
        }
    }

}