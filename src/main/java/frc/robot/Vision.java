package frc.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
    public static double xCenterOfTarget = 0;
    public static double yCenterOfTarget = 0;
	public static double yError,xError;
	
	public static double distance = 0;
	public static double centerDistance = 0;
	
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

	

	public static double distance() {
		
		double[] heightArray = null;
		double[] widthArray = null;
		double[] centerXArray = null;

		NetworkTable _table;
		_table = NetworkTable.getTable("GRIP/myContoursReport");
		heightArray = _table.getNumberArray("height", heightArray);
		widthArray = _table.getNumberArray("width", heightArray);
		centerXArray = _table.getNumberArray("centerX", centerXArray);

		try{
			if(widthArray.length == 2 && heightArray.length == 2 ){
				double height1 = heightArray[0];
				double height2 = heightArray[1];
				double width1 = widthArray[0];
				double width2 = widthArray[1];

				double centerX1 = centerXArray[0];
				double centerX2 = centerXArray[1];

				if(Math.abs(height1 - height2) < 10 && Math.abs(width1 - width2) < 10){

					centerDistance = Math.abs(centerX1 - centerX2);

					distance = 10.8*160/(2*centerDistance*Math.tan(Math.toRadians(20.854)));

					return distance - 10;
				}else{
					return 40;
				}

			}else{
				return 40;
			}

		}catch(Exception e){
			return 40;
		}
	}

}