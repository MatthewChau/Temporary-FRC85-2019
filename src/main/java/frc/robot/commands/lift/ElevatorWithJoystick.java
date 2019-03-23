/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.OI;
import frc.robot.Variables;
import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorWithJoystick extends Command {

    public ElevatorWithJoystick() { // pass in 0.0 if you want stuff to work fine
        requires(Elevator.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Elevator.getInstance().verticalShift(OI.getInstance().getOperatorJoystickY());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !OI.getInstance().getOperatorElevator();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Elevator.getInstance().setElevatorMotors(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
