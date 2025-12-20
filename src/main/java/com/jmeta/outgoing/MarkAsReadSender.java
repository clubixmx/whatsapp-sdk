package com.jmeta.outgoing;

import reactor.core.publisher.Mono;

public interface MarkAsReadSender {
    Mono<Void> markAsRead(String messageId);
}
