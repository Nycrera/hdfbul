package com.kafka.pubsub;

import message.types.Status;

public interface ISensorStatusListener {
	void KafkaAction(Status data);
}