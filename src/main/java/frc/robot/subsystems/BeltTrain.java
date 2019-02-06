package frc.robot.subsystems;
    
import frc.robot.Addresses;
import frc.robot.OI;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


public class BeltTrain extends Subsystem {
    
    public static BeltTrain _instance = null;

    private TalonSRX _belttrainfrontmotor, _belttrainbackmotor;
    private Solenoid _beltSolenoid;

    public BeltTrain() {
        _belttrainfrontmotor = new TalonSRX(Addresses.BELTTRAIN_FRONT_MOTOR);
        _belttrainbackmotor = new TalonSRX(Addresses.BELTTRAIN_BACK_MOTOR);
        
        _beltSolenoid = new Solenoid(Addresses.BELT_SOLENOID);
    }
    
    public boolean getBeltSolenoid() {
      return _beltSolenoid.get();
    }

    public void setBeltSolenoid(boolean on) {
            _beltSolenoid.set(on);
    }

    public static BeltTrain getInstance() {
        if (_instance == null) {
            _instance = new BeltTrain();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {

    }

    public void DriveBelt(double speed) {
     _belttrainbackmotor.set(ControlMode.PercentOutput, speed);
     _belttrainfrontmotor.set(ControlMode.PercentOutput, speed);

    }
     
}
