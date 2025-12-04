package com.jmeta.incoming.mapper.processor;


import com.jmeta.incoming.mapper.IncomingMessageMapper;
import com.jmeta.incoming.message.IncomingMessage;
import com.jmeta.incoming.processor.MessageProcessor;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageProcessorTest {
    private MessageProcessor messageProcessor;

    @Test
    void should_process_text_message() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/contracts/incoming/hello_world.json")) {
            assertNotNull(is, "resource not found");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Optional<IncomingMessage> optional = IncomingMessageMapper.map(json);
            assertTrue(optional.isPresent(), "expected an incoming message");
            IncomingMessage incoming = optional.get();

            assertNotNull(incoming, "incoming message should not be null");
            assertNotNull(incoming.type(), "incoming message type should not be null");
        }
    }

    @Test
    void should_ignore_status_message() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/contracts/incoming/sample_read_message.json")) {
            assertNotNull(is, "resource not found");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Optional<IncomingMessage> optional = IncomingMessageMapper.map(json);
            assertTrue(optional.isEmpty(), "expected mapper to ignore status/read callbacks");
        }
    }
}
