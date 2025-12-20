package com.jmeta.outgoing.message;

import lombok.Builder;

@Builder
public record TypingIndicator(String type) {
    public TypingIndicator {
        if (type == null || type.isBlank()) {
            type = "text";
        }
    }
}
