package com.jmeta.api.config;

import com.jmeta.api.handler.HealthCheckHandler;
import com.jmeta.api.handler.IncomingMessageHandler;
import com.jmeta.incoming.config.WhatsappHookProperties;
import com.jmeta.incoming.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(WhatsappHookProperties.class)
@RequiredArgsConstructor
public class HandlerConfig {

    private final Map<String, MessageProcessor> messageProcessorMap;

    @Bean
    public HealthCheckHandler healthCheckHandler(WhatsappHookProperties properties) {
        return new HealthCheckHandler(properties.getVerifyToken());
    }

    @Bean
    public IncomingMessageHandler incomingMessageHandler(WhatsappHookProperties properties) {
        return new IncomingMessageHandler(messageProcessorMap);
    }
}
