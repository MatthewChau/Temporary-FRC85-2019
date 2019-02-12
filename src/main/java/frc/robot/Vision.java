package frc.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
    public static double xCenterOfTarget = 0;
    public static double yCenterOfTarget = 0;
	public static double yError, xError;
	
	public static double distance = 0;
	public static double centerDistance = 0;

	private static Vision _instance = null;

	private Vision() {
		//don't really have much to do here for init'ing but whatever, can't be overloading the ram with new vision processes
	}

	public static Vision getInstance() {
		if (_instance == null) {
			_instance = new Vision();
		}
		return _instance;
	}
	
	/*********************
	 * TWO TARGET THINGS * - activated by X BUTTON
	 *********************/

	public double getAreaDifference() {
		double[] areaTable = null;
		double[] centerXTable = null;
		double center1, center2, rightArea, leftArea;
		
		try {
			NetworkTable _table;
	 		_table = NetworkTable.getTable("GRIP/myContoursReport");
	 		areaTable = _table.getNumberArray("area", areaTable);
			centerXTable = _table.getNumberArray("centerX", centerXTable);
			
	 		center1 = centerXTable[0];
			center2 = centerXTable[1];
			 
	 		if (center1 > center2) {
	 			rightArea = areaTable[0];
	 			leftArea = areaTable[1];
	 		} else { // should still effectively do the same thing as before
	 			rightArea = areaTable[1];
	 			leftArea = areaTable[0];
	 		}

			return leftArea - rightArea;

		} catch(Exception e) {
			return 0.0;
		}
	}
	
	public double twoTargetCenter() {
		try {
			double[] centerXArray = null;
			
			NetworkTable _table;
			_table = NetworkTable.getTable("GRIP/myContoursReport");
			centerXArray = _table.getNumberArray("centerX", centerXArray);
			
			double centerX1 = centerXArray[0];
			double centerX2 = centerXArray[1];
			
			xCenterOfTarget = (centerX1 + centerX2) / 2; //finds the center of the two targets; where the hook would be
			
			xError = xCenterOfTarget - 80; //distance from target, assumes image is 160p

			//SmartDashboard.putNumber("centerXOfTarget", xCenterOfTarget);
			SmartDashboard.putNumber("x_error", xError);
			
			return xError;		
		} catch(Exception e) {
			return 0;
		}
	}

	public double twoTargetDistance() {
		
		double[] heightArray = null;
		double[] widthArray = null;
		double[] centerXArray = null;

		NetworkTable _table;
		_table = NetworkTable.getTable("GRIP/myContoursReport");
		heightArray = _table.getNumberArray("height", heightArray);
		widthArray = _table.getNumberArray("width", heightArray);
		centerXArray = _table.getNumberArray("centerX", centerXArray);

		try {
			if (widthArray.length == 2 && heightArray.length == 2) {
				double height1 = heightArray[0];
				double height2 = heightArray[1];
				double width1 = widthArray[0];
				double width2 = widthArray[1];

				double centerX1 = centerXArray[0];
				double centerX2 = centerXArray[1];

				if (Math.abs(height1 - height2) < 10 && Math.abs(width1 - width2) < 10){

					centerDistance = Math.abs(centerX1 - centerX2);

					distance = 10.8*160/(2*centerDistance*Math.tan(Math.toRadians(20.854)));

					return distance;
				} else {
					return 0.0;
				}

			} else {
				return 0.0;
			}

		} catch(Exception e) {
			return 0.0; // use a number so that we can tell it isn't working
		}
	}

	/*********************
	 * ONE TARGET THINGS * - activated by Y BUTTON
	 *********************/

	/**
	 * @return degrees the robot needs to turn to be aligned with the alignment line. Counter-clockwise is positive.
	 */
	public double turnAngle() {

		double ratio = 2.0;
		NetworkTable _table;
		double[] angleArray = {80.0, 100.0};
		double angle = 90.0;
		double angle1 = 0.0;
		double angle2 = 0.0;
		double turn = 0.0;
		double pointX = 0.0;
		double pointY = 0.0;
		double reflectedAngle = 0.0;
		double angleRadians = 0.0;
		double slope = 0.0;
		double topViewSlope = 0.0;
        double topViewAngle = 0.0;
        double topViewAngleDegrees = 0.0;

		_table = NetworkTable.getTable("GRIP/myLinesReport");
		angleArray = _table.getNumberArray("angle", angleArray);
        
		if (angleArray.length > 1) {
			angle1 = angleArray[0];
			angle2 = angleArray[1];
            
			if (angle1 < 0) {
				angle1 += 180.0;
			} else if (angle1 >= 180.0) {
				angle1 -= 180.0;
			}
			
			if (angle2 < 0) {
				angle2 += 180.0;
			} else if (angle2 >= 180.0) {
				angle2 -= 180.0;
			}
            
			angle = (angle1 + angle2) / 2.0;
		}

		if (angle > 89 && angle < 91) {
			return 0.0;
		}

		reflectedAngle = 180.0 - angle;
		angleRadians = reflectedAngle * Math.PI / 180.0;
		pointX = Math.cos(angleRadians);
		pointY = Math.sin(angleRadians);

		slope = pointY / pointX;
		topViewSlope = slope * ratio;
        topViewAngle = Math.atan(topViewSlope);

		if (topViewAngle < 0) {
			topViewAngle += Math.PI;
		} else if (topViewAngle > (Math.PI)) {
			topViewAngle -= Math.PI;
		}

		topViewAngleDegrees = topViewAngle * 180.0 / Math.PI;
        turn = -(90 - topViewAngleDegrees);

		return turn;
	}

	public double oneTargetCenter() {
		NetworkTable table;
		double[] x1Array = {0.0, 0.0}, x2Array = {0.0, 0.0};
		//double x11, x12, x21, x22;

		table = NetworkTable.getTable("GRIP/myLinesReport");
		x1Array = table.getNumberArray("x1", x1Array);
		x2Array = table.getNumberArray("x2", x2Array);

		if ((x1Array.length + x2Array.length) > 3) { // maybe there's a way to conglomerate all the lines together.  look into it?
			SmartDashboard.putNumber("x1 for vis", x1Array[0]);
			SmartDashboard.putNumber("x2 for vis", x1Array[1]);
			SmartDashboard.putNumber("x3 for vis", x2Array[0]);
			SmartDashboard.putNumber("x4 for vis", x2Array[1]);
			return ((x1Array[0] + x1Array[1] + x2Array[0] + x2Array[1]) / 4);
		}
		return 0.0;
	}

	public double oneTargetAngle() { // given the white line
		NetworkTable _table;
		double[] angleArray = {0.0, 0.0};
		double angle1, angle2;

		_table = NetworkTable.getTable("GRIP/myLinesReport");
		angleArray = _table.getNumberArray("angle", angleArray);
		
		if (angleArray.length > 1) {
			angle1 = angleArray[0];
			angle2 = angleArray[1];
            
			return ((angle1 + angle2) / 2.0);
		}
		return 0.0; // no turning i guess
	}

}