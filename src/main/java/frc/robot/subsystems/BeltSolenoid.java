package frc.robot.subsystems;
    
import frc.robot.OI;
import frc.robot.Addresses;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class BeltSolenoid extends Subsystem {
    
    public static BeltSolenoid _instance = null;

    private Solenoid _leftBeltSolenoid, _rightBeltSolenoid;

    public BeltSolenoid() {
        _leftBeltSolenoid = new Solenoid(Addresses.BELTTRAIN_LEFT_SOLENOID);
        _rightBeltSolenoid = new Solenoid(Addresses.BELTTRAIN_RIGHT_SOLENOID);
    }

    public static BeltSolenoid getInstance() {
        if (_instance == null) {
            _instance = new BeltSolenoid();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
        
    }

    public void setBeltSolenoid(boolean activated) {
        _leftBeltSolenoid.set(activated);
        _rightBeltSolenoid.set(activated);
    }

    public boolean getLeftBeltSolenoid() {
        return _leftBeltSolenoid.get();
    }

    public boolean getRightBeltSolenoid() {
        return _rightBeltSolenoid.get();
    }
   
}
     

