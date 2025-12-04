package com.jmeta.incoming.message;

public sealed interface IncomingMessage permits IncomingTextMessage {
    Profile profile();
    String type();
}