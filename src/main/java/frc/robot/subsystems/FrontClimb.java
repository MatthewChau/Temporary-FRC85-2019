package frc.robot.subsystems;
   
import frc.robot.Addresses;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class FrontClimb extends Subsystem {

    private static FrontClimb _instance = null;

    private CANSparkMax _frontClimbMotorOne, _frontClimbMotorTwo;

    private FrontClimb() {
        _frontClimbMotorOne = new CANSparkMax(Addresses.FRONT_CLIMB_MOTOR_ONE, CANSparkMaxLowLevel.MotorType.kBrushless);

        _frontClimbMotorTwo = new CANSparkMax(Addresses.FRONT_CLIMB_MOTOR_TWO, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public static FrontClimb getInstance() {
        if (_instance == null) {
            _instance = new FrontClimb();
        }
        return _instance;
    }

    @Override
    public void initDefaultCommand() {
    }

    public void setFrontClimbMotors(double speed) {
    }

    public int getFrontClimbPosition() {
        return 0;
    }

    public void setFrontClimbPosition(int position) {
    }

}