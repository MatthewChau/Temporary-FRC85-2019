package frc.robot.sensors;

import frc.robot.Addresses;

import edu.wpi.first.wpilibj.I2C;

public class RangeFinder {

	private static final double feetPerCentimeter = 0.0328084;

	private static RangeFinder instance = null;
	private I2C rangeFinder = new I2C(I2C.Port.kOnboard, Addresses.RANGEFINDER);

	private byte[] buffer = new byte[2];
	private Thread thread;
	private int range;

	public static RangeFinder getInstance() {
		if (instance == null) {
			instance = new RangeFinder();
		}

		return instance;
	}

	private RangeFinder() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						triggerRead(rangeFinder, Addresses.RANGEFINDER);
						Thread.sleep(80);
					    range = readSensor(rangeFinder, Addresses.RANGEFINDER);
					} catch (Exception ex) {
						System.out.println("Error in range finder loop: " + ex.toString());
					}
				}
			}
		});
        thread.start();
	}

	private void triggerRead(I2C device, int address) {
		try {
			device.write(address, 81);
		} catch (Exception ex) {
			System.out.println("Error triggering range finder at address '" + address + "': " + ex.toString());
		}
	}

	private int readSensor(I2C device, int address) {
		try {
			if (device.read(address, 2, buffer)) {
				return -2;
			}

			short msb = (short) (buffer[0] & 0x7F);
			short lsb = (short) (buffer[1] & 0xFF);
			return msb * 256 + lsb;
		} catch (Exception ex) {
			System.out.println("Error reading value from range finder at address '" + address + "': " + ex.toString());
		}

		return -1;
	}

	public double getDistance() {
		return  range * feetPerCentimeter;
	}

}