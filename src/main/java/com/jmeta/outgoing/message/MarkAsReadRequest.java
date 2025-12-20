package com.jmeta.outgoing.message;

import lombok.Builder;

@Builder
public record MarkAsReadRequest(String messagingProduct,
                                String status,
                                String messageId) {

    public MarkAsReadRequest {
        if (messagingProduct == null || messagingProduct.isBlank()) {
            messagingProduct = "whatsapp";
        }
        if (status == null || status.isBlank()) {
            status = "read";
        }
    }
}
