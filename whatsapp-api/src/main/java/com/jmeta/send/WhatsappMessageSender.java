package com.jmeta.send;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RequiredArgsConstructor
public class WhatsappMessageSender implements MessageSender{
    private final String metaUrl;
    private final String token;

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
}
