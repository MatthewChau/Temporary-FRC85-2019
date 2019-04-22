package frc.robot.subsystems;

import frc.robot.Addresses;

import frc.robot.commands.climb.ClimbRearDriveWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ClimbRearDrive extends Subsystem {

    private static ClimbRearDrive _instance = null;

    private TalonSRX _climbRearDriveMotor;

    private ClimbRearDrive() {
        _climbRearDriveMotor = new TalonSRX(Addresses.CLIMB_REAR_DRIVE);
        _climbRearDriveMotor.setNeutralMode(NeutralMode.Brake);

        _climbRearDriveMotor.configOpenloopRamp(0.5);
    }

    public static ClimbRearDrive getInstance() {
        if (_instance == null) {
            _instance = new ClimbRearDrive();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ClimbRearDriveWithJoystick());
    }

    // CLIMB DRIVE MOTOR

    public void moveClimbRearDrive(double speed) {
        // decide that pid exists here maybe?
    }

    public void setClimbRearDriveMotor(double speed) {
        _climbRearDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public int getClimbRearDriveMotorPosition() {
        return _climbRearDriveMotor.getSelectedSensorPosition();
    }

    public void setClimbRearDriveMotorPosition(int position) {
        _climbRearDriveMotor.setSelectedSensorPosition(position);
    }

}