package com.jmeta.api.processor;

import com.jmeta.api.processor.message.IncomingMessage;
import com.jmeta.api.processor.message.IncomingTextMessage;
import com.jmeta.send.MessageSender;
import com.jmeta.send.TextContent;
import com.jmeta.send.WhatsappMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TextMessageProcessor implements MessageProcessor {

    private final MessageSender messageSender;

    @Override
    public void process(IncomingMessage incomingMessage) {
        IncomingTextMessage incomingTextMessage = (IncomingTextMessage) incomingMessage;
        log.info("Processing TextMessage:{}",incomingTextMessage.text());
        messageSender.send(WhatsappMessage.builder()
                        .messagingProduct("whatsapp")
                        .recipientType("individual")
                        .to("525511566012")
                        .type("text")
                        .text(TextContent.builder().body(String.format("Echo: %s",incomingTextMessage.text())).build())
                        .build())
                .subscribe();
    }
}
