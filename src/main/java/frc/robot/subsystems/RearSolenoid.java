package frc.robot.subsystems;
   
import frc.robot.Addresses;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class RearSolenoid extends Subsystem {
    
    public static RearSolenoid _instance = null;

    private Solenoid _rearSolenoid1, _rearSolenoid2;

    public RearSolenoid() {
        _rearSolenoid1 = new Solenoid(Addresses.REAR_SOLENOID_1);
        _rearSolenoid2 = new Solenoid(Addresses.REAR_SOLENOID_2);
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
        _rearSolenoid1.set(activated);
        _rearSolenoid2.set(!activated);
    }

    public boolean getRearSolenoidOne() { // this should be the state of the system as a whole anyway
        return _rearSolenoid1.get();
    }

    public boolean getRearSolenoidTwo() {
        return _rearSolenoid2.get();
    }
   
}
     

