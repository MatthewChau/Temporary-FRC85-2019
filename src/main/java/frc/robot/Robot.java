/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.sensors.IMU;
import frc.robot.sensors.Sensors;
import frc.robot.subsystems.ClimbRear;
import frc.robot.subsystems.ClimbRearDrive;
import frc.robot.subsystems.ClimbFront;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Interruptable;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Mast;
import frc.robot.subsystems.Spike;
import frc.robot.Diagnostics;
import frc.robot.Vision;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Arrays;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    public static boolean overrideLimits = false;

    private Diagnostics _diagnostics;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        IMU.getInstance();
        Sensors.getInstance();

        ClimbFront.getInstance();
        ClimbRear.getInstance();
        ClimbRearDrive.getInstance();
        DriveTrain.getInstance();
        Elevator.getInstance();
        Intake.getInstance();
        Mast.getInstance();
        Spike.getInstance();

        OI.getInstance();
        Variables.getInstance();
        Vision.getInstance();

        Interruptable.getInstance();

        _diagnostics = new Diagnostics();
        _diagnostics.log();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        Variables.getInstance().outputVariables();
    }

    @Override
    public void autonomousInit() {
        IMU.getInstance().setFusedHeading(0);
        
        teleopInit();

        // init the pid stuff 

        Arrays.fill(OI.getInstance().firstRun, true);
        Arrays.fill(OI.getInstance().errorSum, 0.0);
        Arrays.fill(OI.getInstance().lastActual, 0.0);
    }

    /**
     * This function is called periodically during ̶a̶u̶t̶o̶n̶o̶m̶o̶u̶s̶  sandstorm.
     */
    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void teleopInit() {
        Sensors.getInstance().startTimers();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        Sensors.getInstance().checkSensorsForEncoderReset();

        if ((ClimbRear.getInstance().getAdjustingBool() || ClimbRear.getInstance().getBothAdjustingBool() || OI.getInstance().getClimbRearJoystickButton())
            && (DriverStation.getInstance().getMatchTime() > 0.5 || DriverStation.getInstance().getMatchTime() < 0)) {
            ClimbRear.getInstance().setServo(Variables.getInstance().getClimbUnlocked());
        } else {
            ClimbRear.getInstance().setServo(Variables.getInstance().getClimbLocked());
        }

        if (Elevator.getInstance().getAdjustingBool() || OI.getInstance().getElevatorJoystickButton()) {
            Elevator.getInstance().setServo(Variables.getInstance().getElevatorUnlocked());
        } else {
            Elevator.getInstance().setServo(Variables.getInstance().getElevatorLocked());
            Elevator.getInstance().resetTimer();
        }

        if (SmartDashboard.getBoolean("Run Diagnostics?", false)) {
            _diagnostics.log();
        }

        if (DriverStation.getInstance().getMatchTime() < 0.3) {
            _diagnostics.close();
        }

        if (SmartDashboard.getBoolean("Relay?", false)) {
            Spike.getInstance().setRelay(true);
        } else {
            Spike.getInstance().setRelay(false);
        }
    }

    @Override
    public void testInit() {
        _diagnostics.log();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledPeriodic() {
        Elevator.getInstance().setTargetPosition(Elevator.getInstance().getElevatorPosition());
        Mast.getInstance().setTargetPosition(Mast.getInstance().getMastPosition());
        Intake.getInstance().setTargetPos(Intake.getInstance().getWristPosition());
        Sensors.getInstance().stopTimers();
        IMU.getInstance().setInitialYPR();
        IMU.getInstance().setFusedHeading(0);

        Scheduler.getInstance().removeAll();

        if (SmartDashboard.getBoolean("Close Diagnostics?", true)) {
            _diagnostics.close();
        }
    }

}