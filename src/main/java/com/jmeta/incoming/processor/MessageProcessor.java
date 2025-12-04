package com.jmeta.incoming.processor;


import com.jmeta.incoming.message.IncomingMessage;

@FunctionalInterface
public interface MessageProcessor {
    void process(IncomingMessage messageJson);
}
