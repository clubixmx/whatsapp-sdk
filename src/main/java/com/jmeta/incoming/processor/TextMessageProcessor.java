package com.jmeta.incoming.processor;


import com.jmeta.incoming.message.IncomingMessage;
import com.jmeta.incoming.message.IncomingTextMessage;
import com.jmeta.outgoing.MessageSender;
import com.jmeta.outgoing.message.TextContent;
import com.jmeta.outgoing.message.WhatsappMessage;
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
