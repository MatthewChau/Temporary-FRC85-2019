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

    public double oneTargetCenter() {
        NetworkTable table;
        double[] x1Array = {0.0, 0.0}, x2Array = {0.0, 0.0};

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

    /**
     * @return degrees the robot needs to turn to be aligned with the alignment line (+ is clockwise)
     */
    public double alignmentLine() {
        double a, b, c, d, f, g, h, i, x, m1, m2, rotate = 0;
        double[] x1 = {0, 0}, x2 = {0, 0}, y1 = {0, 0}, y2 = {0, 0}, len = {0, 0};
        double max = 0;
        double secondMax = 0;
        int longest = 0;
        int secondLongest = 1;
        double imgWidth = Variables.IMAGE_WIDTH;
        double FOV = Variables.FOV;
        
        NetworkTable table = NetworkTable.getTable("GRIP/myLinesReport");
        x1 = table.getNumberArray("x1", x1);
        x2 = table.getNumberArray("x2", x2);
        y1 = table.getNumberArray("y1", y1);
        y2 = table.getNumberArray("y2", y2);
        len = table.getNumberArray("length", len);

        if (len.length > 1) {
            if (len.length > 2) {
                // find the two longest lines
                for (int j = 0; j < len.length; j++) {
                    if (len[j] > max) {
                        max = len[j];
                        longest = j;
                    } else if (len[j] > secondMax) {
                        secondMax = len[j];
                        secondLongest = j;
                    }
                }
            }

            // line 1: (a, b) to (c, d)
            a = x1[longest];
            b = y1[longest];
            c = x2[longest];
            d = y2[longest];
            // line 2: (f, g) to (h, i)
            f = x1[secondLongest];
            g = y1[secondLongest];
            h = x2[secondLongest];
            i = y2[secondLongest];

            // calculate x value of intersection of lines
            m1 = (d-b)/(c-a);
            m2 = (i-g)/(h-f);
            x = (m1*a - m2*f + g - b)/(m1 - m2);

            // adjust center
            x -= imgWidth/2;

            // convert to degrees
            rotate = x/imgWidth*FOV;

            // make sure it isn't NaN
            if (rotate == rotate) {
                return rotate;
            }
        }

        return 0;
    }
}