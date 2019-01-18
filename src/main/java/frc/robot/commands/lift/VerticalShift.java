package frc.robot.commands.lift;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Addresses;
import frc.robot.subsystems.Lift;

public class VerticalShift extends Command {
    private int _direction; //positive means going up, negative means going down
    private int _currentPhase; //The current phase just before this command is called
    private boolean _moving = false;
    public VerticalShift(int direction) {
        requires(Lift.getInstance());
        _direction = direction;
        _currentPhase = Lift.getInstance().getPhase();
    }

    @Override
    protected void initialize() {
        super.initialize();
        Lift.getInstance().changePhase(_direction);
        if (Lift.getInstance().getPhase() != _currentPhase) {
            Lift.getInstance().verticalShift(-_direction); //since forward is -1, and backwards is +1
            _moving = true;
        }
        
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        if (Lift.getInstance().getPhase() == 0) {
            if (_direction > 0) {
                return (Lift.getInstance().getLeftEncoderValue() >= Addresses.LIFT_PHASE_0); //checks to see if passes target if going up
            }
            else if (_direction < 0) {
                return (Lift.getInstance().getLeftEncoderValue() <= Addresses.LIFT_PHASE_0); //checks to see if passes target if going down
            }
        }
        else if (Lift.getInstance().getPhase() == 1) {
            if (_direction > 0) {
                return (Lift.getInstance().getLeftEncoderValue() >= Addresses.LIFT_PHASE_1); //checks to see if passes target if going up
            }
            else if (_direction < 0) {
                return (Lift.getInstance().getLeftEncoderValue() <= Addresses.LIFT_PHASE_1); //checks to see if passes target if going down
            }
        }
        else if (Lift.getInstance().getPhase() == 2) {
            if (_direction > 0) {
                return (Lift.getInstance().getLeftEncoderValue() >= Addresses.LIFT_PHASE_2); //checks to see if passes target if going up
            }
            else if (_direction < 0) {
                return (Lift.getInstance().getLeftEncoderValue() <= Addresses.LIFT_PHASE_2); //checks to see if passes target if going down
            }
        }
        else if (Lift.getInstance().getPhase() == 3) {
            if (_direction > 0) {
                return (Lift.getInstance().getLeftEncoderValue() >= Addresses.LIFT_PHASE_3); //checks to see if passes target if going up
            }
            else if (_direction < 0) {
                return (Lift.getInstance().getLeftEncoderValue() <= Addresses.LIFT_PHASE_3); //checks to see if passes target if going down
            }
        }
        return !_moving; //Should only return this if something failed or if lift is not at target yet, or if lift will not be moving at all
    }

    @Override
    protected void end() {
        super.end();
        Lift.getInstance().verticalShift(0); //stop the motor
    }
}