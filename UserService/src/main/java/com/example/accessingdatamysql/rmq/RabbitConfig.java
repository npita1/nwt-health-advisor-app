package com.example.accessingdatamysql.rmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "user-queue";
    public static final String ROLLBACK_QUEUE = "user-rollback-queue";
    public static final String EXCHANGE = "user-exchange";
    public static final String ROUTING_KEY = "user-routingKey";
    public static final String ROLLBACK_ROUTING_KEY = "user-rollback-routingKey";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }
    @Bean
    public Queue rollbackQueue() {
        return new Queue(ROLLBACK_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }
    @Bean
    public Binding rollbackBinding(){
        return BindingBuilder.bind(rollbackQueue()).to(exchange()).with(ROLLBACK_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter());
        return template;
    }
}