package com.jmeta.outgoing.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WhatsappMarkAsReadSenderTest {

    private final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);


    @Test
    void should_produce_json_for_mark_message_as_read() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();

            // mock success response
            server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

            // point the sender to the mock server
            String baseUrl = server.url("/messages").toString();
            WhatsappMarkAsReadSenderSender sender = new WhatsappMarkAsReadSenderSender(baseUrl, "TEST_TOKEN");

            String messageId = "wamid.234123";
            String waId = "5511566012";

            // perform the mark-as-read and block
            sender.markAsRead(waId, messageId).block();

            // capture the actual request body
            okhttp3.mockwebserver.RecordedRequest recorded = server.takeRequest();
            String actualJson = recorded.getBody().readString(StandardCharsets.UTF_8);

            // load expected contract from resources
            try (InputStream is = getClass().getResourceAsStream("/contracts/outgoing/mark_as_read.json")) {
                assertTrue(is != null, "Contract file not found in resources");
                String expectedJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                JsonNode expectedNode = mapper.readTree(expectedJson);
                JsonNode actualNode = mapper.readTree(actualJson);
                assertEquals(expectedNode, actualNode, "Sent JSON does not match contract");
            }
        }
    }
}
