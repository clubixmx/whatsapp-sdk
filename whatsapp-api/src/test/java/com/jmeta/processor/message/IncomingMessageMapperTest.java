package com.jmeta.processor.message;

import com.jmeta.api.processor.message.IncomingMessage;
import com.jmeta.api.processor.message.IncomingMessageMapper;
import com.jmeta.api.processor.message.IncomingTextMessage;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IncomingMessageMapperTest {

    @Test
    void should_map_incoming_text_message() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/contracts/incoming/hello_world.json")) {
            assertNotNull(is, "resource not found");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            IncomingMessage incoming = IncomingMessageMapper.map(json);

            // basic assertions (optional)
            assertEquals("Jonathan", incoming.profile().name());
            assertEquals("525511566012", incoming.profile().waId());
            assertEquals("text", incoming.type());
            if (incoming.type().equals("text")) {
                IncomingTextMessage message = (IncomingTextMessage) incoming;
                assertEquals("Hola", message.text());
            }
        }
    }
}
