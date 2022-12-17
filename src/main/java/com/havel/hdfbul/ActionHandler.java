package com.havel.hdfbul;

import com.kafka.pubsub.ActionConsumer;
import com.kafka.pubsub.IActionListener;

import message.types.Action;

// Action handler creates its own consumer!
public class ActionHandler implements IActionListener {

	public ActionHandler() {
		ActionConsumer ac = new ActionConsumer();
		ac.registerActionListener(this);
		ac.startConsumer();
	}

	@Override
	public void KafkaAction(Action data) {
		switch (data.getAction()) {
		case "START":
			App.startSensor();
			break;
		case "STOP":
			App.stopSensor();
			break;
		}
	}

}
