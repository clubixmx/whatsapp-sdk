package com.jmeta.send;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WhatsappTextMessageTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Test
    void should_produce_text_message() throws Exception {
        WhatsappMessage whatsappMessage = WhatsappMessage.builder()
            .to("525511566012")
            .type("text")
            .text(
                TextContent.builder()
                    .body("Hello World")
                    .build()
            )
            .build();
        
        JsonNode actual = MAPPER.valueToTree(whatsappMessage);

        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("contracts/outgoing/hello_world.json")) {
            assertNotNull(is, "Contract file not found at `src/test/resources/contracts/outgoing/hello_world.json`");
            JsonNode expected = MAPPER.readTree(is);
            assertEquals(expected, actual, "Produced payload must match the contract JSON");
        }

    }
}
