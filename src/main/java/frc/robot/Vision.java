package frc.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
    public static double xCenterOfTarget = 0;
    public static double yCenterOfTarget = 0;
	public static double yError,xError;
	
	public static double distance = 0;
	public static double centerDistance = 0;

	public static double rotate(){

		double[] areaTable = null;
		double[] centerXTable = null;
		double center1, center2, rightArea, leftArea;
		double _leftTurn = 0.1;
		double _rightTurn = -0.1;
		double _areaTolerence = 20;
		try{
			NetworkTable _table;
			_table = NetworkTable.getTable("GRIP/myContoursReport");
			areaTable = _table.getNumberArray("area", areaTable);
			centerXTable = _table.getNumberArray("centerX", centerXTable);

			center1 = centerXTable[0];
			center2 = centerXTable[1];

			if (center1 > center2){
				rightArea = areaTable[0];
				leftArea = areaTable[1];
			}
			else if (center2 > center1){
				rightArea = areaTable[1];
				leftArea = areaTable[0];
			}else{
				rightArea = 0;
				leftArea = 0;
			}

			if(rightArea > leftArea + _areaTolerence){
				return _rightTurn;
			}else if (leftArea > rightArea + _areaTolerence){
				return _leftTurn;
			}else{
				return 0;
			}
		}catch(Exception e){
			return 0;
		}
		
	}
	
	public static double centerX() {
		try{
			double[] centerXArray = null;
			
			NetworkTable _table;
			_table = NetworkTable.getTable("GRIP/myContoursReport");
			centerXArray = _table.getNumberArray("centerX", centerXArray);
			
			double centerX1 = centerXArray[0];
			double centerX2 = centerXArray[1];
			
			xCenterOfTarget = (centerX1 + centerX2) / 2; //finds the center of the two targets; where the hook would be
			
			xError = xCenterOfTarget - 80; //distance from target, assumes image is 160p

			SmartDashboard.putNumber("centerXOfTarget", xCenterOfTarget);
			SmartDashboard.putNumber("x_error", xError);
			
			return xError;		
		}catch (Exception e){
			return 0;
		}
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