/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.lift.VerticalShift;
import frc.robot.subsystems.DriveTrain;
import frc.robot.commands.drivetrain.FollowTarget;
import frc.robot.commands.lift.HorizontalShift;

public class OI {

    private static OI _instance;

    private Joystick _leftJoystick;
    private Joystick _rightJoystick;
    private JoystickButton _operatorPhaseZero, _operatorPhaseOne, _operatorPhaseTwo, _operatorPhaseThree, _driverLeftBumper, _driverRightBumper, _driverA, _driverB;

    private double _xSpeed = 0, _ySpeed = 0, _zRotation = 0;

    private double _gyroAngle;

    // Toggable value to change whether the robot drives headed or headless
    private boolean _headed = true;

    private OI() {
        _leftJoystick = new Joystick(Addresses.LEFT_JOYSTICK);
        _rightJoystick = new Joystick(Addresses.RIGHT_JOYSTICK);

        _operatorPhaseZero = new JoystickButton(_rightJoystick, 1); //Change values if needed
        _operatorPhaseOne = new JoystickButton(_rightJoystick, 2); //Change values if needed
        _operatorPhaseTwo = new JoystickButton(_rightJoystick, 3); //Change values if needed
        _operatorPhaseThree = new JoystickButton(_rightJoystick, 4); //Change values if needed

        _driverLeftBumper = new JoystickButton(_leftJoystick, 1);//Change values if needed
        _driverRightBumper = new JoystickButton(_leftJoystick, 2);//Change values if needed
        _driverA = new JoystickButton(_leftJoystick, 3);//Change values if needed

        _operatorPhaseZero.whenPressed(new VerticalShift(0, 1)); //go to phase 0
        _operatorPhaseOne.whenPressed(new VerticalShift(1, 1)); //go to phase 1
        _operatorPhaseTwo.whenPressed(new VerticalShift(2, 1)); //go to phase 2
        _operatorPhaseThree.whenPressed(new VerticalShift(3, 1)); //go to phase 3

        _driverLeftBumper.whenActive(new HorizontalShift(0, 1)); //1 means to go left (or backward) hopefully
        _driverRightBumper.whenActive(new HorizontalShift(1, -1)); //-1 means to go right (or forward) hopefully
        
        FollowTarget followTarget;
        _driverA.whenPressed(followTarget = new FollowTarget()); //follows when pressed
        _driverB.cancelWhenPressed(followTarget); //cancels following when pressed
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
        _xSpeed = _leftJoystick.getX();
        _ySpeed = _leftJoystick.getY();
        _zRotation = _leftJoystick.getZ();
    }

    private void headlessDrive() {
        _xSpeed = _leftJoystick.getX();
        _ySpeed = _leftJoystick.getY();
        _zRotation = _leftJoystick.getZ();
        //_gyroAngle = 
    }

}