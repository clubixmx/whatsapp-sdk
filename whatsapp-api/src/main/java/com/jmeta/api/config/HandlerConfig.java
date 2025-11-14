package com.jmeta.api.config;

import com.jmeta.api.handler.WhatsappHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WhatsappHookProperties.class)
public class HandlerConfig {

    @Bean
    public WhatsappHandler whatsappHandler (WhatsappHookProperties properties) {
        return new WhatsappHandler(properties.getVerifyToken());
    }
}
