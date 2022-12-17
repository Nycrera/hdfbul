package com.havel.hdfbul;

import java.util.Enumeration;
import java.util.Hashtable;

import message.types.Status;

public class CoreUnit {
	Hashtable<String, SensorRecord> sensorTable = new Hashtable<String, SensorRecord>();

	public CoreUnit() {

	}

	public Target getPredictedTarget() throws Exception {
		Target target = new Target();
		if (sensorTable.size() < 2) {
			target.posX = 0.0; // Should have instead throw an exception and handle it. But it's fine for now.
			target.posY = 0.0;
		} else if(sensorTable.size() == 2) { // We assume that we have 2 sensors for now, but maybe we can extend for more
					// later.
			SensorRecord sensor;
			Enumeration<String> e = sensorTable.keys();
			// First line m*x + n = y
			sensor = sensorTable.get(e.nextElement());
			
			double m1 = Math.tan(Math.toRadians(sensor.angle));
			m1 = 1/m1; // conver to cotangent
			double n1 = sensor.posY - (m1 * sensor.posX);
			// Second line m*x + n = y
			sensor = sensorTable.get(e.nextElement());
			double m2 = Math.tan(Math.toRadians(sensor.angle));
			m2 = 1/m2;
			double n2 = sensor.posY - (m2 * sensor.posX);
			// Solve for a solution x = (n2-n1) / (m1-m2) and y = m1*x+n1
			target.posX = (n2-n1) / (m1-m2);
			target.posY = m1 * target.posX + n1;
			
		}else {
			throw new Exception("More than 2 sensors exists in the system, which is not supported.");
		}

		return target;
	}

	public void ProcessNewData(Status data) {

		SensorRecord newRecord = new SensorRecord();
		newRecord.angle = data.getAngle();
		newRecord.posX = data.getSensorPosX();
		newRecord.posY = data.getSensorPosY();
		sensorTable.put(data.getName(), newRecord);

		/*
		 * // We already have a previous record, update the new data
		 * if(sensorList.containsKey(data.getName())){
		 * 
		 * }else { // New sensor detected, register SensorRecord newRecord = new
		 * SensorRecord(); newRecord.angle = data.getAngle(); newRecord.posX =
		 * data.getSensorPosX(); newRecord.posY = data.getSensorPosY();
		 * sensorList.put(data.getName(), newRecord); }
		 */
	}

}
