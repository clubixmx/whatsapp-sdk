package com.jmeta.send;

import lombok.Builder;

@Builder
public record WhatsappMessage(String messagingProduct, String recipientType, String to, String type, TextContent text) {
    public WhatsappMessage {
        if (messagingProduct == null || messagingProduct.isBlank()) {
            messagingProduct = "whatsapp";
        }
        if (recipientType == null || recipientType.isBlank()) {
            recipientType = "individual";
        }
    }
}