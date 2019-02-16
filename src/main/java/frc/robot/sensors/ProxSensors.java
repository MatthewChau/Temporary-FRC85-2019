/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Addresses;

public class ProxSensors extends Subsystem {

    private static ProxSensors _instance = null;
    private DigitalInput _liftTopLimit, _liftBottomLimit, _liftFrontLimit, _liftRearLimit;

    private DigitalInput _intakeTopLimit, _intakeBottomLimit;

    private ProxSensors() {
        _liftTopLimit = new DigitalInput(Addresses.LIFT_TOP_LIMIT);
        _liftBottomLimit = new DigitalInput(Addresses.LIFT_BOTTOM_LIMIT);

        _liftFrontLimit= new DigitalInput(Addresses.LIFT_FRONT_LIMIT);
        _liftRearLimit = new DigitalInput(Addresses.LIFT_BACK_LIMIT);

        _intakeTopLimit = new DigitalInput(Addresses.INTAKE_TOP_LIMIT);
        _intakeBottomLimit = new DigitalInput(Addresses.INTAKE_BOTTOM_LIMIT);
    }

    public static ProxSensors getInstance() {
        if (_instance == null) {
            _instance = new ProxSensors();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public boolean getLiftTopLimit() {
        return _liftTopLimit.get();
    }

    public boolean getLiftBottomLimit() {
        return _liftBottomLimit.get();
    }

    public boolean getLiftFrontLimit() {
        return _liftFrontLimit.get();
    }

    public boolean getLiftRearLimit() {
        return _liftRearLimit.get();
    }

    public boolean getIntakeTopLimit() {
        return _intakeTopLimit.get();
    }

    public boolean getIntakeBottomLimit() {
        return _intakeBottomLimit.get();
    }

}