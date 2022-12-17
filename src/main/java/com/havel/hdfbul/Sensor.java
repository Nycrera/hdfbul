package com.havel.hdfbul;

import message.types.Status;

public class Sensor {
	private String Name;
	public Double posX;
	public Double posY;
	private Target target = null;

	public Sensor(String name, Double x, Double y) {
		Name = name;
		posX = x;
		posY = y;
	}

	public void setTarget(Target newTarget) {
		target = newTarget;
	}

	public Status ProduceData() {
		Status sensorData = new Status();
		sensorData.setName(Name);
		sensorData.setSensorPosX(posX);
		sensorData.setSensorPosY(posY);
		sensorData.setAngle(calculateAngle());
		return sensorData;
	}

	private Double calculateAngle() {
		if (target != null) {
			double result = Math.toDegrees(Math.atan((target.posX - posX) / (target.posY - posY)));
			// Re-range from -180 to 180 to 0 to 360 with modulo
			return (result + 360) % 360;
		} else {
			throw new NullPointerException("Sensor does not have a target.");
		}
	}
}
