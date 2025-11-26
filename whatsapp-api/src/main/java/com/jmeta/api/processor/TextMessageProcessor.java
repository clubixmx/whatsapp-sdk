package com.jmeta.api.processor;

import com.jmeta.api.processor.message.IncomingMessage;
import com.jmeta.api.processor.message.IncomingTextMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextMessageProcessor implements MessageProcessor {
    @Override
    public void process(IncomingMessage incomingMessage) {
        IncomingTextMessage incomingTextMessage = (IncomingTextMessage) incomingMessage;
        log.info("Processing TextMessage:{}",incomingTextMessage.text());
        try {
            Thread.sleep(3000); // Simulate processing delay
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("TextMessage:{} Completed!",incomingTextMessage.text());
    }
}
