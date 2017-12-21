package com.phpdaddy.importcustomerservice.rabbit.producer;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportCustomerProducerConfig {
    @Bean
    public Exchange eventExchange() {
        return new TopicExchange("eventExchange");
    }
}
