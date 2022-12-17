package com.kafka.pubsub;

import com.kafka.connection.IAppConfigs;
import com.kafka.serialization.ObjectDeserializer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import message.types.Status;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SensorStatusConsumer {
	static Logger logger = LogManager.getLogger(SensorStatusProducer.class.getName());

	public static ISensorStatusListener KafkaListener;

	public void registerActionListener(ISensorStatusListener listener) {
		KafkaListener = listener;
	}

	public void startConsumer() {
		KafkaConsumer<String, Status> kafkaConsumer = getKafkaConsumer();
		kafkaConsumer.subscribe(Collections.singletonList(IAppConfigs.SENSOR_STATUS_TOPIC));
		logger.info("Consumer initialized!");
		while (true) {
			try {
				ConsumerRecords<String, Status> statusRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
				statusRecords.forEach(record -> {

					KafkaListener.KafkaAction(record.value());
					System.out.println(record.value().toString());
				});
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
	}

	public static KafkaConsumer<String, Status> getKafkaConsumer() {
		KafkaConsumer<String, Status> kafkaConsumer = new KafkaConsumer<String, Status>(getKafkaConsumerConfig());
		return kafkaConsumer;
	}

	private static Properties getKafkaConsumerConfig() {
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, IAppConfigs.APPLICATION_ID_CONFIG);
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IAppConfigs.BOOTSTAP_SERVER);
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ObjectDeserializer.class);
		// consumerProps.put(CustomDeserializer.VALUE_CLASS_NAME_CONFIG,
		// OrderInvoice.class);
		Random generator = new Random();
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, String.valueOf(generator.nextInt(10000)) + "havelsan");
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return consumerProps;
	}
}