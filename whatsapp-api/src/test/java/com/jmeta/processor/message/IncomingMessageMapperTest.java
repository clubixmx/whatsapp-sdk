package com.jmeta.processor.message;

import com.jmeta.api.processor.message.IncomingMessage;
import com.jmeta.api.processor.message.IncomingMessageMapper;
import com.jmeta.api.processor.message.IncomingTextMessage;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class IncomingMessageMapperTest {

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
}
