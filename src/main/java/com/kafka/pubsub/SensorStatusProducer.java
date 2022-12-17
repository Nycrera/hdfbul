package com.kafka.pubsub;

import com.kafka.connection.IAppConfigs;
import com.kafka.serialization.ObjectSerializer;

import java.util.Properties;
import message.types.Status;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SensorStatusProducer {

	static Logger logger = LogManager.getLogger(SensorStatusProducer.class.getName());
	static KafkaProducer<String, Status> kafkaProducer = new KafkaProducer<String, Status>(getKafkaProducerConfig());

	public static void Send(Status sensorStatus) throws Exception {
		ProducerRecord<String, Status> statusRecord = new ProducerRecord<>(IAppConfigs.SENSOR_STATUS_TOPIC, "status",
				sensorStatus);
		kafkaProducer.send(statusRecord);
		logger.info("Status Event published...");
	}

	public static void Close() throws InterruptedException {
		kafkaProducer.flush();
		kafkaProducer.close();
		Thread.sleep(2);
		logger.info("Producer closed...");
	}

	private static Properties getKafkaProducerConfig() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IAppConfigs.BOOTSTAP_SERVER);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ObjectSerializer.class);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, IAppConfigs.APPLICATION_ID_CONFIG);
		return props;
	}

}