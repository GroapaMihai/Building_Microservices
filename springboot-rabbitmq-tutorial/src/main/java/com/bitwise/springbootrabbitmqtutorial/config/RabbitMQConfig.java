package com.bitwise.springbootrabbitmqtutorial.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.json.key}")
    private String jsonRoutingKey;

    // spring bean for RabbitMQ Queue
    @Bean
    public Queue getQueue() {
        return new Queue(queue);
    }

    // spring bean for RabbitMQ Json Queue
    @Bean
    public Queue getJsonQueue() {
        return new Queue(jsonQueue);
    }

    // spring bean for RabbitMQ Exchange
    @Bean
    public TopicExchange getExchange() {
        return new TopicExchange(exchange);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding getBinding() {
        return BindingBuilder
            .bind(getQueue())
            .to(getExchange())
            .with(routingKey);
    }

    // binding between json queue and exchange using routing key
    @Bean
    public Binding getJsonBinding() {
        return BindingBuilder
                .bind(getJsonQueue())
                .to(getExchange())
                .with(jsonRoutingKey);
    }

    @Bean
    public MessageConverter getJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate getAmqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(getJsonMessageConverter());

        return rabbitTemplate;
    }

    // Following 3 beans are automatically configured, we don't have to explicitly define them
    // Connection factory
    // RabbitTemplate
    // RabbitAdmin
}
