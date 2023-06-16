package com.bitwise.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.producer.bitwise.topic}")
    private String bitwiseTopic;

    @Value("${spring.kafka.producer.bitwise.json.topic}")
    private String bitwiseJsonTopic;

    @Bean
    public NewTopic bitwiseTopic() {
        return TopicBuilder.name(bitwiseTopic)
            .build();
    }

    @Bean
    public NewTopic bitwiseJsonTopic() {
        return TopicBuilder.name(bitwiseJsonTopic)
                .build();
    }
}
