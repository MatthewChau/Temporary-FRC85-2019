package frc.robot.subsystems;
   
import frc.robot.Addresses;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class RearSolenoid extends Subsystem {
    
    public static RearSolenoid _instance = null;

    private Solenoid _rearSolenoid;

    public RearSolenoid() {
        _rearSolenoid = new Solenoid(Addresses.REAR_SOLENOID);
    }

    public static RearSolenoid getInstance() {
        if (_instance == null) {
            _instance = new RearSolenoid();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void setRearSolenoid(boolean activated) {
        _rearSolenoid.set(activated);
    }

    public boolean getRearSolenoid() {
        return _rearSolenoid.get();
    }
   
}
     

