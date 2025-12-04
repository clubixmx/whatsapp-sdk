package com.jmeta.outgoing.sender;

import com.jmeta.outgoing.message.MessageResponse;
import com.jmeta.outgoing.message.WhatsappMessage;
import reactor.core.publisher.Mono;

public interface MessageSender {
    Mono<MessageResponse> send(WhatsappMessage whatsappMessage);
}
