package com.kafka.pubsub;

import com.kafka.connection.IAppConfigs;
import com.kafka.serialization.ObjectDeserializer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import message.types.Action;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionConsumer {
	static Logger logger = LogManager.getLogger(ActionProducer.class.getName());

	public static IActionListener KafkaListener;

	public void registerActionListener(IActionListener listener) {
		KafkaListener = listener;
	}

	public void startConsumer() {
		KafkaConsumer<String, Action> kafkaConsumer = getKafkaConsumer();
		kafkaConsumer.subscribe(Collections.singletonList(IAppConfigs.ACTION_TOPIC));
		logger.info("Consumer initialized!");
		while (true) {
			try {
				ConsumerRecords<String, Action> actionRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
				actionRecords.forEach(record -> {

					// Something happened
					KafkaListener.KafkaAction(record.value());
					System.out.println(record.value().toString());
				});
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
	}

	public static KafkaConsumer<String, Action> getKafkaConsumer() {
		KafkaConsumer<String, Action> kafkaConsumer = new KafkaConsumer<String, Action>(getKafkaConsumerConfig());
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