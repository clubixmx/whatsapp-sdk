package com.jmeta.incoming.message;

public record IncomingTextMessage(Profile profile, Message message, String type, String text) implements IncomingMessage {
    public IncomingTextMessage {
        if (profile == null) throw new IllegalArgumentException("profile is required");
        if (message == null) throw new IllegalArgumentException("message is required");
        if (type == null) type = "text";
        if (text == null) text = "";
    }
}
