package com.jmeta.outgoing.message;

import lombok.Builder;

@Builder
public record TypingIndicatorRequest(String messagingProduct,
                                     String status,
                                     String messageId,
                                     TypingIndicator typingIndicator) {

    public TypingIndicatorRequest {
        if (messagingProduct == null || messagingProduct.isBlank()) {
            messagingProduct = "whatsapp";
        }
        if (status == null || status.isBlank()) {
            status = "read";
        }
        if (typingIndicator == null) {
            typingIndicator = TypingIndicator.builder().build();
        }
    }
}
