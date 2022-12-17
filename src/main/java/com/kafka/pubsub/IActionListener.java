package com.kafka.pubsub;

import message.types.Action;

public interface IActionListener {
	void KafkaAction(Action data);
}