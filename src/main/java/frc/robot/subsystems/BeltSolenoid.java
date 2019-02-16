package frc.robot.subsystems;
    
import frc.robot.OI;
import frc.robot.Addresses;
import frc.robot.Variables;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class BeltSolenoid extends Subsystem {
    
    public static BeltSolenoid _instance = null;

    private Solenoid _beltSolenoid;

    public BeltSolenoid() {
        _beltSolenoid = new Solenoid(Addresses.BELTTRAIN_SOLENOID);
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
        _beltSolenoid.set(activated);
    }

    public boolean getBeltSolenoid() {
        return _beltSolenoid.get();
    }

}
     

