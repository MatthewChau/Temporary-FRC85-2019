/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.sensors.IMU;
import frc.robot.subsystems.Lift;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.commands.lift.HorizontalShift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    private static OI _instance;

    private Joystick _driverController;
    private Joystick _operatorController;
    private JoystickButton _operatorPhaseZero, _operatorPhaseOne, _operatorPhaseTwo, _operatorPhaseThree, _driverLeftBumper, _driverRightBumper;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;
    private double _liftSpeed = 0;

    private double _gyroAngle;

    // Toggable value to change whether the robot drives headed or headless
    private boolean _headed = true;

    private OI() {
        _driverController = new Joystick(Addresses.CONTROLLER_DRIVER);
        _operatorController = new Joystick(Addresses.CONTROLLER_OPERATOR);

        _operatorPhaseZero = new JoystickButton(_operatorController, 1); //Change values if needed
        _operatorPhaseOne = new JoystickButton(_operatorController, 2); //Change values if needed
        _operatorPhaseTwo = new JoystickButton(_operatorController, 3); //Change values if needed
        _operatorPhaseThree = new JoystickButton(_operatorController, 4); //Change values if needed

        _driverLeftBumper = new JoystickButton(_operatorController, 1);
        _driverRightBumper = new JoystickButton(_operatorController, 2);

        _operatorPhaseZero.whenPressed(new VerticalShift(0, 1)); //go to phase 0
        _operatorPhaseOne.whenPressed(new VerticalShift(1, 1)); //go to phase 1
        _operatorPhaseTwo.whenPressed(new VerticalShift(2, 1)); //go to phase 2
        _operatorPhaseThree.whenPressed(new VerticalShift(3, 1)); //go to phase 3

        _driverLeftBumper.whenActive(new HorizontalShift(0, 1)); //1 means to go left (or backward) hopefully
        _driverRightBumper.whenActive(new HorizontalShift(1, -1)); //-1 means to go right (or forward) hopefully
    }

    public static OI getInstance() {
        if (_instance == null) {
            _instance = new OI();
        }
        return _instance;
    }

    public double[] getJoystickInput() {
        if (_headed) {
            headedDrive();
            // 1 or 0 is passed to identify whether the array is headless or headed drive.
            return new double[] {1, _xSpeed, _ySpeed, _zRotation};
        } else {
            headlessDrive();
            return new double[] {0, _xSpeed, _ySpeed, _zRotation, _gyroAngle};
        }
    }

    private void headedDrive() {
        // Deadband is initialized in subsystem DriveTrain with the mecanum drive constructor.
        _xSpeed = -_driverController.getRawAxis(0);
        _ySpeed = _driverController.getRawAxis(1);
        _zRotation = -_driverController.getRawAxis(4);
    }

    private void headlessDrive() {
        _xSpeed = -_driverController.getRawAxis(0);
        _ySpeed = _driverController.getRawAxis(1);
        _zRotation = -_driverController.getRawAxis(4);
        _gyroAngle = IMU.getInstance().getFusedHeading();
    }

    /**
     * Called in commands to return the joystick axis which is converted into the set speed of the motor
     */
    public double getVerticalLift() {
        if (!Lift.getInstance().checkVerticalLift(_operatorController.getRawAxis(1))) {
            return _liftSpeed = 0;
        } else {
            return _liftSpeed = _operatorController.getRawAxis(1);
        }
    }

    public double getHorizontalLift() {
        if (!Lift.getInstance().checkHorizontalLift(_operatorController.getRawAxis(0))) {
            return _liftSpeed = 0;
        } else {
            return _liftSpeed = _operatorController.getRawAxis(0);
        }
    }

}