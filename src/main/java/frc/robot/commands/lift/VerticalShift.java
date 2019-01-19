package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Lift;

public class VerticalShift extends Command {

    private int _speed, _targetPhase; //positive means going up, negative means going down
    private double _liftTolerance = 10; //the motors have to be within 10 encoder ticks in order for the command to stop

    public VerticalShift(int phase, int speed) {
        requires(Lift.getInstance());
        _targetPhase = phase;
        _speed = speed;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Lift.getInstance().verticalShift(_speed);
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(Lift.getInstance().getLeftEncoderValue() - Lift.getInstance().getPhaseValue(_targetPhase)) <= _liftTolerance);
    }

    @Override
    protected void end() {
        super.end();
        Lift.getInstance().verticalShift(0);
    }

}