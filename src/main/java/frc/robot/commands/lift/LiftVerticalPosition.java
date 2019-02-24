/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.LiftVertical;

import edu.wpi.first.wpilibj.command.Command;

public class LiftVerticalPosition extends Command {

    private int _target;
    public LiftVerticalPosition(int target) {
        requires(LiftVertical.getInstance());
        _target = target;
    }

    @Override
    protected void execute() {
        super.execute();
        LiftVertical.getInstance().setTargetPosition(_target);
        LiftVertical.getInstance().changeAdjustingBool(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

}