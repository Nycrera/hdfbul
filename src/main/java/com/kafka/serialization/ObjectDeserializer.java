package com.kafka.serialization;

import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class ObjectDeserializer<T extends Serializable> implements Deserializer<T> {

	public static final String VALUE_CLASS_NAME_CONFIG = "value.class.name";

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(String topic, byte[] objectData) {
		return (objectData == null) ? null : (T) SerializationUtils.deserialize(objectData);
	}

	@Override
	public void close() {
	}
}