package com.jmeta.send;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderTest {

    private final MessageSender messageSender = new MessageSender() {

        private String metaUrl = "https://graph.facebook.com/v22.0/886634904532563/messages";
        private String token = "EAAfTjwe2ZC4gBQGMK4ulOYKjAlJqwIbC8pvZCGAJxatwBrx18eTgg4iTUs9KhjQGpDC9ZCsSXDUezSP8aoHXlUkIKnSvlzvRt80EAl1TYi7TOFzkHec5sx779ECJdVwhfaWIC94s4Dw6hc0fLcIJiFZBLNTenNX931L9ROe3qjYRtwe3Snm9dFCuWdBkIk9aZCJiuq3mwVV6aLknfmiMzoZCrqs2MQAMKBqF0FEJGomu8te2iJ0qX6wca4IHOVsWZBPyTfqpVCmaCZA49XeYCGlRywZCAJJl3rijs";

        private final ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE) // `snake_case` for WhatsApp API
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        private final HttpClient httpClient = HttpClient.newHttpClient();

        @Override
        public MessageResponse send(WhatsappMessage whatsappMessage) {
            try {
                String json = mapper.writeValueAsString(Objects.requireNonNull(whatsappMessage, "whatsappMessage"));

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(metaUrl))
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                return new MessageResponse(response.statusCode(), response.body());
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return new MessageResponse(499, "Request interrupted");
            } catch (IOException ioe) {
                return new MessageResponse(500, ioe.getMessage());
            }
        }
    };

    @Test
    public void should_send_text_message() {
        String messagingProduct = "whatsapp";
        String recipientType = "individual";
        String to = "525511566012";
        String type = "text";
        String textBody = "Hello, this is a test message.";

        MessageResponse response = messageSender.send(
                WhatsappMessage.builder()
                        .messagingProduct(messagingProduct)
                        .recipientType(recipientType)
                        .to(to)
                        .type(type)
                        .text(TextContent.builder()
                                .body(textBody)
                                .build())
                        .build()
        );

        assertEquals(200, response.statusCode());
    }
}
