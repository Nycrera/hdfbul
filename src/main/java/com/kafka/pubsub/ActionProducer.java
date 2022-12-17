package com.kafka.pubsub;

import com.kafka.connection.IAppConfigs;
import com.kafka.serialization.ObjectSerializer;

import java.util.Properties;
import message.types.Action;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionProducer {

	static Logger logger = LogManager.getLogger(ActionProducer.class.getName());
	static KafkaProducer<String, Action> kafkaProducer = new KafkaProducer<String, Action>(getKafkaProducerConfig());

	public static void Send(String ActionString) throws Exception {
		Action action = new Action();
		action.setAction(ActionString);
		ProducerRecord<String, Action> actionRecord = new ProducerRecord<>(IAppConfigs.ACTION_TOPIC, "action", action);
		kafkaProducer.send(actionRecord);
		logger.info("Event published...");
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