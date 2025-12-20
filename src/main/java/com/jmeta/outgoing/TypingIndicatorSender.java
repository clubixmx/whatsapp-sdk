package com.jmeta.outgoing;

import reactor.core.publisher.Mono;

public interface TypingIndicatorSender {
    Mono<Void> send(String messageId);
}
