package com.jmeta.api.config;

import com.jmeta.send.MessageSender;
import com.jmeta.send.WhatsappMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WhatsappProperties.class)
@RequiredArgsConstructor
public class MessageSenderConfig {

    private final WhatsappProperties whatsappProperties;

    @Bean
    public MessageSender messageSender() {
        return new WhatsappMessageSender(whatsappProperties.getMetaUrl(),whatsappProperties.getToken());
    }
}
