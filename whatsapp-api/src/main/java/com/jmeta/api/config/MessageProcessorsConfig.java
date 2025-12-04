package com.jmeta.api.config;

import com.jmeta.api.processor.MessageProcessor;
import com.jmeta.api.processor.TextMessageProcessor;
import com.jmeta.send.MessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageProcessorsConfig {

    @Bean
    public MessageProcessor textMessageProcessor(MessageSender messageSender) {
        return new TextMessageProcessor(messageSender);
    }
}
