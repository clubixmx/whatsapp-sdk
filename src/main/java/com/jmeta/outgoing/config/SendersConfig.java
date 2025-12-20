package com.jmeta.outgoing.config;


import com.jmeta.outgoing.MarkAsReadSender;
import com.jmeta.outgoing.MessageSender;
import com.jmeta.outgoing.TypingIndicatorSender;
import com.jmeta.outgoing.impl.WhatsappMarkAsReadSender;
import com.jmeta.outgoing.impl.WhatsappMessageSender;
import com.jmeta.outgoing.impl.WhatsappTypingIndicatorSender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WhatsappProperties.class)
@RequiredArgsConstructor
public class SendersConfig {

    private final WhatsappProperties whatsappProperties;

    @Bean
    public MessageSender messageSender() {
        return new WhatsappMessageSender(whatsappProperties.getMetaUrl(),whatsappProperties.getToken());
    }

    @Bean
    public MarkAsReadSender markAsRead() {
        return new WhatsappMarkAsReadSender(whatsappProperties.getMetaUrl(),whatsappProperties.getToken());
    }

    @Bean
    public TypingIndicatorSender typingIndicatorSender() {
        return new WhatsappTypingIndicatorSender(whatsappProperties.getMetaUrl(),whatsappProperties.getToken());
    }

}
