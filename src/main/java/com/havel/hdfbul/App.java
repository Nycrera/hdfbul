package com.havel.hdfbul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import com.kafka.pubsub.ActionProducer;
import com.kafka.pubsub.SensorStatusProducer;

import message.types.Status;

public class App {
	static CoreUnit core_unit;
	static Sensor sensor;
	static String TYPE = null;
	static Timer timer = new Timer();

	public static void main(String[] args) throws Exception {
		System.out.println("Select a procedure by typing \"SENSOR\" or \"CORE\"");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(TYPE == null) {
			String input = reader.readLine().toUpperCase().trim();
			if(input.equals("SENSOR") || input.equals("CORE")) TYPE = input;
			else System.out.println("Invalid Input");
		}
		if (TYPE.equals("CORE")) {
			System.out.println("Started program as the Core Data Processing Unit");
			core_unit = new CoreUnit();

			// Run Kafka reader in another thread to not block the further user input.
			Runnable runnable = () -> {
				@SuppressWarnings("unused")
				SensorDataHandler sensorHandler = new SensorDataHandler();
			};
			Thread DataReceiverThread = new Thread(runnable);
			DataReceiverThread.start();

			System.out.println("Core Unit is ready.");
			System.out.println("Press enter to start, if the sensors are ready.");
			reader.readLine();
			ActionProducer.Send("START");
			System.out.println("Start command sent.\n Data will be printed every 10 second");
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					Target target;
					try {
						target = core_unit.getPredictedTarget();
						System.out.printf("Target at -> (%.2f,%.2f)%n", target.posX, target.posY);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 10 * 1000, 10 * 1000);

		} else if (TYPE.equals("SENSOR")) {
			System.out.println("Started program as a Sensor");
			System.out.println("Sensor Name:");
			String sensorName = reader.readLine();
			if(sensorName.isBlank()) {
				System.err.println("Invalid sensor name.");
				return;
			}
			try {
			System.out.println("Sensor Position in X axis:");
			Double posX = Double.parseDouble(reader.readLine());
			System.out.println("Sensor Position in Y axis:");
			Double posY = Double.parseDouble(reader.readLine());
			System.out.println("Target Position in X axis:");
			Target target = new Target();
			target.posX = Double.parseDouble(reader.readLine());
			System.out.println("Target Position in Y axis:");
			target.posY = Double.parseDouble(reader.readLine());
			sensor = new Sensor(sensorName, posX, posY);
			sensor.setTarget(target);
			} catch(NumberFormatException e) {
				System.err.println("Invalid Input. (NumberFormatException)");
			}

			// Run Kafka reader in another thread to not block the further user input.
			Runnable runnable = () -> {
				@SuppressWarnings("unused")
				ActionHandler actionhandler = new ActionHandler();
			};
			Thread ActionReceiverThread = new Thread(runnable);
			ActionReceiverThread.start();
			System.out.println("Sensor created and ready.");
			while (true) {
				System.out.println("To move the target press enter.");
				reader.readLine();
				try {
				Target newTarget = new Target();
				System.out.println("Target Position in X axis:");
				newTarget.posX = Double.parseDouble(reader.readLine());
				System.out.println("Target Position in Y axis:");
				newTarget.posY = Double.parseDouble(reader.readLine());
				sensor.setTarget(newTarget);
				} catch(NumberFormatException e) {
					System.err.println("Invalid Input. (NumberFormatException)");
				}
				System.out.println("Target moved.");
			}
		} else {
			System.out.println("Non-valid App.TYPE");
		}
	}

	public static void startSensor() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Status data = sensor.ProduceData();
				try {
					SensorStatusProducer.Send(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 2 * 1000, 2 * 1000); // Send data every 2 seconds
	}

	public static void stopSensor() {
		timer.cancel();
	}
}
