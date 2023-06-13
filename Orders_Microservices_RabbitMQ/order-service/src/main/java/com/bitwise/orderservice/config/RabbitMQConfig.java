package com.bitwise.orderservice.config;

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

    @Value("${rabbitmq.order.queue.name}")
    private String orderQueue;

    @Value("${rabbitmq.email.queue.name}")
    private String emailQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;

    // spring bean for Queue - Order Queue
    @Bean
    public Queue getOrderQueue() {
        return new Queue(orderQueue);
    }

    // spring bean for Queue - Email Queue
    @Bean
    public Queue getEmailQueue() {
        return new Queue(emailQueue);
    }

    // spring bean for exchange
    @Bean
    public TopicExchange getExchange() {
        return new TopicExchange(exchange);
    }

    // spring bean for binding between exchange and order queue
    @Bean
    public Binding getOrderBinding() {
        return BindingBuilder
                .bind(getOrderQueue())
                .to(getExchange())
                .with(orderRoutingKey);
    }

    // spring bean for binding between exchange and email queue
    @Bean
    public Binding getEmailBinding() {
        return BindingBuilder
                .bind(getEmailQueue())
                .to(getExchange())
                .with(emailRoutingKey);
    }

    // message converter
    @Bean
    public MessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate getAmqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(getMessageConverter());

        return rabbitTemplate;
    }
}
