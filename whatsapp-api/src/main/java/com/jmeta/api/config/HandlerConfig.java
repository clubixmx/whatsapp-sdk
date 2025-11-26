package com.jmeta.api.config;

import com.jmeta.api.handler.HealthCheckHandler;
import com.jmeta.api.handler.IncomingMessageHandler;
import com.jmeta.api.processor.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(WhatsappHookProperties.class)
public class HandlerConfig {

    @Bean
    public HealthCheckHandler healthCheckHandler(WhatsappHookProperties properties) {
        return new HealthCheckHandler(properties.getVerifyToken());
    }

    @Bean
    public IncomingMessageHandler incomingMessageHandler(WhatsappHookProperties properties) {
        return new IncomingMessageHandler(messageProcessorMap());
    }

    @Bean
    public Map<String,MessageProcessor> messageProcessorMap() {
        return Map.of(
                "text", new com.jmeta.api.processor.TextMessageProcessor()
        );
    }
}
