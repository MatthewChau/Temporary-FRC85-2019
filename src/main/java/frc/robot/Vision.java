package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
    public static double xCenterOfTarget = 0;
    public static double yCenterOfTarget = 0;
	public static double yError,xError;
	
	public static double distance;
	
	public static double centerX() {
		
		double[] centerXArray = null;
		
		NetworkTable _table;
		_table = NetworkTable.getTable("GRIP/myContoursReport");
		centerXArray = _table.getNumberArray("centerX", centerXArray);
		
		double centerX1 = centerXArray[0];
		double centerX2 = centerXArray[1];
		
		xCenterOfTarget = (centerX1 + centerX2) / 2; //finds the center of the two targets; where the hook would be
		
		xError = xCenterOfTarget - 160; //distance from target, assumes image is 320p

		SmartDashboard.putNumber("centerXOfTarget", xCenterOfTarget);
		SmartDashboard.putNumber("x_error", xError);
		
		return xError;		
	}

	

	public static double distance(){
		
		double[] widthArray = null;

		NetworkTable _table;
		_table = NetworkTable.getTable("GRIP/myContoursReport");
		widthArray = _table.getNumberArray("width", widthArray);
		double width = widthArray[0];

		distance = (0.0033707969*(Math.pow((width), 4))) + (-0.2609546004*(Math.pow((width), 3))) + (7.5147768862*(Math.pow((width), 2))) + (-98.23654097*width) + (534.8078762);

		return distance;
	}

}