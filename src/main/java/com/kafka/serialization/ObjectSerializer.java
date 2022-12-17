package com.kafka.serialization;

import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serializer;

public class ObjectSerializer<T extends Serializable> implements Serializer<T> {
	public ObjectSerializer() {
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public byte[] serialize(String topic, T data) {
		return SerializationUtils.serialize(data);
	}

	@Override
	public void close() {
	}
}
