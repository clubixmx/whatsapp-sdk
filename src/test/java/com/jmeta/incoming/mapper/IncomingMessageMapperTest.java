package com.jmeta.incoming.mapper;


import com.jmeta.incoming.message.IncomingMessage;
import com.jmeta.incoming.message.Message;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncomingMessageMapperTest {

    @Test
    void should_process_text_message() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/contracts/incoming/hello_world.json")) {
            assertNotNull(is, "resource not found");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Optional<IncomingMessage> optional = IncomingMessageMapper.map(json);
            assertTrue(optional.isPresent(), "expected an incoming message");
            IncomingMessage incoming = optional.get();
            Message message = incoming.message();
            assertNotNull(message, "message should not be null");

            assertNotNull(incoming, "incoming message should not be null");
            assertNotNull(incoming.type(), "incoming message type should not be null");

        }
    }
}
