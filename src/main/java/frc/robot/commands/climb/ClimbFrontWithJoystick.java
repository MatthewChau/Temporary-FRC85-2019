/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climb;

import frc.robot.OI;
import frc.robot.subsystems.ClimbFront;
import frc.robot.sensors.IMU;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbFrontWithJoystick extends Command {

    private double _speed;
    private double _multiplier;

    public ClimbFrontWithJoystick() {
        requires(ClimbFront.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        IMU.getInstance().setClimbYPR();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        _speed = OI.getInstance().getOperatorJoystickY();

        if (_speed > 0) {
            _multiplier = 0.9;
        } else {
            _multiplier = 0.9; // because ryan is a well-to-do individual.
        }
        ClimbFront.getInstance().moveClimbFront(_speed * _multiplier);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !OI.getInstance().getClimbFrontJoystickButton();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        ClimbFront.getInstance().setClimbFrontMotors(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}

/*

josiah, the slowboy:
                    .--,
     .-.    __,,,__/    |
    /   \-'`        `-./_
    |    |               `)
     \   `             `\ ;
    /       ,        ,    |
    |      /         : O /_
    |          O  .--;__   '.
    |                (  )`.  |
    \                 `-` /  |
     \          ,_  _.-./`  /        apparently this is supposed to be bored
      \          \``-.(    /
      |           `---'   /--.
    ,--\___..__        _.'   /--.
    \          `-._  _`/    '    '.
    .' ` ' .       ``    '        .

RYAN, who likes his things fast:

            _____----''''       
   ----''''             |
  |                      |
  |                      |
  .                      .
   |                      |
   |                  ____|--                     
   |      ____----''''    ;
  --'''''      _     ,-'  |
    |       .'   '.  : O /_
    |       '. _ .'-;__    '.
    |                (  )`.  |
    \                 `-` /  |
     \         -,_  _.-./`  /        help i can't make a smirk
      \          '         /
      |                   /--.
    ,--\___..__        _.'   /--.
    \          `-._  _`/    '    '.
    .' ` ' .       ``    '        .
*/