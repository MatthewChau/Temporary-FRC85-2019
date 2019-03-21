/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import frc.robot.Addresses;
import frc.robot.subsystems.Elevator;
 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;

public class IMU {

    private static IMU _instance;

    private PigeonIMU _pigeon;

    private PigeonIMU.GeneralStatus _genStatus = new PigeonIMU.GeneralStatus();
    private short[] _xyz = new short[3];

    private double _initialHeading = 0;
    private double[] _initialYPR = new double[3];

    private IMU() {
        _pigeon = new PigeonIMU(Elevator.getInstance().getIMUTalon());
    }

    public static IMU getInstance() {
        if (_instance == null) {
            _instance = new IMU();
        }
        return _instance;
    }

    public PigeonIMU getIMU() {
        return _pigeon;
    }

    public ErrorCode getStatus() {
        return _pigeon.getGeneralStatus(_genStatus);
    }

    public void setFusedHeading(double ang) {
        _pigeon.setFusedHeading(ang);
    }
    
    public double getFusedHeading() {
        return _pigeon.getFusedHeading();
    }

    public double[] getYPR() {
        double[] _ypr = new double[3];
        ErrorCode error = _pigeon.getYawPitchRoll(_ypr);
        SmartDashboard.putString("IMU Error Code", error.toString());
        return _ypr;
    }

    public void setInitialYPR() {
        _initialYPR = getYPR();
    }

    public double getInitialYaw() {
        return _initialYPR[0];
    }

    public double getInitialPitch() {
        return _initialYPR[1];
    }

    public double getInitialRoll() {
        return _initialYPR[2];
    }

    public double getYaw() {
        double[] _ypr = getYPR();
        return _ypr[0] - _initialYPR[0];
    }

    public double getPitch() {
        double[] _ypr = getYPR();
        return _ypr[2] - _initialYPR[2];
    }

    public double getRoll() {
        double[] _ypr = getYPR();
        return _ypr[1] - _initialYPR[1];
    }

}