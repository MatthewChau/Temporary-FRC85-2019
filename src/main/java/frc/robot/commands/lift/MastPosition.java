/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import frc.robot.subsystems.Mast;

import edu.wpi.first.wpilibj.command.Command;

public class MastPosition extends Command {

    private int _target, __target;

    public MastPosition(int target) {
        requires(Mast.getInstance());
        _target = target;
    }

    @Override
    protected void initialize() {
        __target = _target;
    }

    @Override
    protected void execute() {
        super.execute();
        Mast.getInstance().setTargetPosition(__target);
        Mast.getInstance().changeAdjustingBool(true);
        Mast.getInstance().horizontalShift(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !Mast.getInstance().getAdjustingBool();
    }

}