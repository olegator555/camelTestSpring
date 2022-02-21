package com.example.csv2jmscamel.config;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    @Bean
    JmsComponent createJmsComponent(final ConnectionFactory connectionFactory) {
        return JmsComponent.jmsComponent(connectionFactory);
    }

}
