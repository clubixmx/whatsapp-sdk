package com.jmeta.incoming.config;


import com.jmeta.incoming.processor.MessageProcessor;
import com.jmeta.incoming.processor.TextMessageProcessor;
import com.jmeta.outgoing.sender.MessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageProcessorsConfig {

    @Bean
    public MessageProcessor textMessageProcessor(MessageSender messageSender) {
        return new TextMessageProcessor(messageSender);
    }
}
