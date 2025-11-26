package com.jmeta.processor;

import com.jmeta.api.processor.MessageProcessor;
import com.jmeta.api.processor.message.IncomingMessage;
import com.jmeta.api.processor.message.IncomingMessageMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageProcessorTest {
    private MessageProcessor messageProcessor;

    @Test
    void should_process_text_message() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/contracts/incoming/hello_world.json")) {
            assertNotNull(is, "resource not found");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            IncomingMessage incoming = IncomingMessageMapper.map(json);

        }
    }
}
