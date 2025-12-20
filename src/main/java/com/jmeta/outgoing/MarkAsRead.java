package com.jmeta.outgoing;

import reactor.core.publisher.Mono;

public interface MarkAsRead {
    Mono<Void> markAsRead(String waId, String messageId);
}
