package com.kafka.connection;

public interface IAppConfigs {
	String BOOTSTAP_SERVER = "localhost:9092";
	String APPLICATION_ID_CONFIG = "havelsan_config";
	String SENSOR_STATUS_TOPIC = "status";
	String ACTION_TOPIC = "action";
	String SENDER_ID = "client_1";
}
