package com.havel.hdfbul;

import com.kafka.pubsub.SensorStatusConsumer;
import com.kafka.pubsub.ISensorStatusListener;

import message.types.Status;

//SensorDataHandler handler creates its own consumer!
public class SensorDataHandler implements ISensorStatusListener {
	App appContext;

	public SensorDataHandler() {
		SensorStatusConsumer ssc = new SensorStatusConsumer();
		ssc.registerActionListener(this);
		ssc.startConsumer();
	}

	@Override
	public void KafkaAction(Status data) {
		App.core_unit.ProcessNewData(data);
	}

}
