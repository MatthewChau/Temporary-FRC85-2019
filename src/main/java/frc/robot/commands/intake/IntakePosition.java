/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakePosition extends Command {
    
    private double _target;

    public IntakePosition(double target) {
        requires(Intake.getInstance());
        _target = target;
    }

    @Override
    protected void execute() {
        super.execute();
        Intake.getInstance().setTargetPos(_target);
        Intake.getInstance().changeAdjustingBool(true);
        Intake.getInstance().setFlipper(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return !Intake.getInstance().getAdjustingBool();
    }

}