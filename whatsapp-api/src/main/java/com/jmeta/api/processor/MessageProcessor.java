package com.jmeta.api.processor;

import com.jmeta.api.processor.message.IncomingMessage;

@FunctionalInterface
public interface MessageProcessor {
    void process(IncomingMessage messageJson);
}
