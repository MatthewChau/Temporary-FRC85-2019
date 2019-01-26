package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Lift;

//UNFINISHED (maybe)

public class HorizontalShift extends Command {

    private int _direction, _targetPhase;
    private int _tolerance;

    public HorizontalShift(int phase, int direction) {
        requires(Lift.getInstance());
        _direction = direction;
        _targetPhase = phase;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Lift.getInstance().horizontalShift(_direction);
        //Change to "Lift.getInstance().horizontalShift(_direction * OI.getInstance().getRawAxis)" 
        //or something like that if you wish to manipulate based on joystick axis.
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(Lift.getInstance().getCimEncoderValue() - Lift.getInstance().getHorizontalPhase(_targetPhase)) <= _tolerance);
    }

    @Override
    protected void end() {
        super.end();
        Lift.getInstance().horizontalShift(0);
    }
}