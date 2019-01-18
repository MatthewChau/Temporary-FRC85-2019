package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Addresses;

/*
    *3 Encoders (1 for slide)
    *2 775s (TalonSRX) for lift up/down
    *4 Proximity sensors (detect prescences of something)
    *1 CIM (TalonSRX) for slide

    2 motors on lift for lift, 1 CIM for 7-inch shift
*/

public class Lift extends Subsystem {
    private static Lift _instance = null;
    private TalonSRX _liftLeft, _liftRight, _cim;
    private Encoder _leftEncoder;
    private int _phase = 0; //0 is ground level, 1 is bottom (of rocket), 2 is middle, 3 is top
    
    public Lift() {
        _liftLeft = new TalonSRX(Addresses.LIFT_LEFT_MOTOR);
        _liftRight = new TalonSRX(Addresses.LIFT_RIGHT_MOTOR);
    }

    @Override
    protected void initDefaultCommand() {
        
    }

    public static Lift getInstance() {
        if (_instance == null) {
            _instance = new Lift();
        }
        return _instance;
    }

    public void verticalShift(int speed) {
        _liftLeft.set(ControlMode.PercentOutput, speed);
        _liftRight.set(ControlMode.PercentOutput, -speed); //Since the motors are gonna be facing different directions. If going wrong direction, make the top line -speed and the bottom one speed.
    }

    public void horizontalShift(int speed) {
        _cim.set(ControlMode.PercentOutput, speed);
    }

    public double getLeftEncoderValue() {
        return _leftEncoder.getDistance();
    }

    public int getPhase() {
        return _phase;
    }

    public void changePhase(int value)
    {
        if (value > 0 && _phase < 3) { //if value is positive
            _phase += value;
        }
        else if (value < 0 && _phase > 0) { //if value is negative
            _phase += value;
        }
    }
}