package com.jmeta.send;

import reactor.core.publisher.Mono;

public interface MessageSender {
    Mono<MessageResponse> send(WhatsappMessage whatsappMessage);
}
