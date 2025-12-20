package com.jmeta.outgoing.config;


import com.jmeta.outgoing.MarkAsReadSender;
import com.jmeta.outgoing.MessageSender;
import com.jmeta.outgoing.impl.WhatsappMarkAsReadSenderSender;
import com.jmeta.outgoing.impl.WhatsappMessageSender;
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

    @Bean
    public MarkAsReadSender markAsRead() {
        return new WhatsappMarkAsReadSenderSender(whatsappProperties.getMetaUrl(),whatsappProperties.getToken());
    }
}
