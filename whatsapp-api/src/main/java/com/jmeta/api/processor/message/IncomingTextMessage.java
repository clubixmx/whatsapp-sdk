package com.jmeta.api.processor.message;

public record IncomingTextMessage(Profile profile, String type, String text) implements IncomingMessage {
    public IncomingTextMessage {
        if (profile == null) throw new IllegalArgumentException("profile is required");
        if (type == null) type = "text";
        if (text == null) text = "";
    }
}
