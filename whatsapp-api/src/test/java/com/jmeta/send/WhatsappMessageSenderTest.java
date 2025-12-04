package com.jmeta.send;

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

public class WhatsappMessageSenderTest {


    private final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @Test
    public void should_send_text_message() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();

            // make the mock server return 200 OK so the sender doesn't fail
            server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));

            // point the sender to the mock server
            String baseUrl = server.url("/messages").toString();
            WhatsappMessageSender sender = new WhatsappMessageSender(baseUrl, "TEST_TOKEN");

            // build the message that should match the contract
            WhatsappMessage message = WhatsappMessage.builder()
                    .messagingProduct("whatsapp")
                    .recipientType("individual")
                    .to("525511566012")
                    .type("text")
                    .text(TextContent.builder().body("Hello, this is a test message.").build())
                    .build();

            // perform the send and block for the reactive result
            MessageResponse response = sender.send(message).block();

            // ensure the mocked server got a request and the client saw 200
            assertEquals(200, response.statusCode());

            // capture the actual request body
            okhttp3.mockwebserver.RecordedRequest recorded = server.takeRequest();
            String actualJson = recorded.getBody().readString(StandardCharsets.UTF_8);

            // load expected contract from resources
            try (InputStream is = getClass().getResourceAsStream("/contracts/outgoing/sample_text_message.json")) {
                assertTrue(is != null, "Contract file not found in resources");
                String expectedJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                // compare as JsonNode to avoid ordering issues and whitespace differences
                JsonNode expectedNode = mapper.readTree(expectedJson);
                JsonNode actualNode = mapper.readTree(actualJson);
                assertEquals(expectedNode, actualNode, "Sent JSON does not match contract");
            }
        }
    }
}
