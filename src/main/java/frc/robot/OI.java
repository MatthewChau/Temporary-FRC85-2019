/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;

public class OI {

    private static OI _instance;

    private Joystick _controller;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;

    private double _gyroAngle;

    // Toggable value to change whether the robot drives headed or headless
    private boolean _headed = true;

    private OI() {
      _controller = new Joystick(Addresses.CONTROLLER);
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
            return new double[] {0, _xSpeed, _ySpeed, _zRotation}; //_gryoAngle};
        }
    }

    private void headedDrive() {
        // Deadband is initialized in subsystem DriveTrain with the mecanum drive constructor.
        _xSpeed = -_controller.getRawAxis(0);
        _ySpeed = _controller.getRawAxis(1);
        _zRotation = -_controller.getRawAxis(4);
    }

    private void headlessDrive() {
        _xSpeed = -_controller.getRawAxis(0);
        _ySpeed = _controller.getRawAxis(1);
        _zRotation = -_controller.getRawAxis(4);
        //_gyroAngle = 
    }

}