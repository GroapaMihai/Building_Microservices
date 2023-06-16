package com.bitwise.springboot.kafka;

import com.bitwise.springboot.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private KafkaTemplate<String, User> kafkaTemplate;

    @Value("${spring.kafka.producer.bitwise.json.topic}")
    private String bitwiseJsonTopic;

    @Autowired
    public JsonKafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User user) {
        Message<User> message = MessageBuilder.withPayload(user)
            .setHeader(KafkaHeaders.TOPIC, bitwiseJsonTopic)
            .build();

        kafkaTemplate.send(message);

        LOGGER.info(String.format("Message sent -> %s", user));
    }
}
