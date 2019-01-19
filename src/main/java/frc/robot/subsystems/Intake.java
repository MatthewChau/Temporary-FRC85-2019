/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Addresses;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The intake subsystem
 */
public class Intake extends Subsystem {

    private static Intake _instance = null;
    private Solenoid _flipper;
    private Solenoid _pusher;
    private TalonSRX _roller1, _roller2;

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    private void Intake() {
        _flipper = new Solenoid(Addresses.INTAKE_FLIPPER);
        _pusher = new Solenoid(Addresses.INTAKE_PUSHER);
        _roller1 = new TalonSRX(Addresses.INTAKE_ROLLER1);
        _roller2 = new TalonSRX(Addresses.INTAKE_ROLLER2);
    }

    public static Intake getInstance() {
        if (_instance == null) {
        _instance = new Intake();
        }
        return _instance;
    }

    public boolean getIntakePosition() {
        return _flipper.get();
    }

    public void flipIntake() {
        if (getIntakePosition()) {
            _flipper.set(false);
        } else {
            _flipper.set(true);
        }
    }

    public void hatchPusher(boolean state) {
        _pusher.set(state);
    }

    public void intakeCargo(double speed) {
        _roller1.set(ControlMode.PercentOutput, speed);
        _roller2.set(ControlMode.PercentOutput, -speed);
    }

}
