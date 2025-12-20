package com.jmeta.outgoing.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.jmeta.outgoing.MarkAsRead;
import com.jmeta.outgoing.message.MarkAsReadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class WhatsappMarkAsReadSender implements MarkAsRead {

    private final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private final WebClient webClient;

    public WhatsappMarkAsReadSender(String metaUrl, String token) {
        this.webClient = WebClient.builder()
                .baseUrl(metaUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public Mono<Void> markAsRead(String waId, String messageId) {
        return Mono.defer(() -> {
            log.info("Marking as read:{}",messageId);
            MarkAsReadRequest request = MarkAsReadRequest.builder()
                    .messageId(messageId)
                    .build();
            try {
                String json = mapper.writeValueAsString(request);
                return webClient.post()
                        .bodyValue(json)
                        .retrieve()
                        .toBodilessEntity()
                        .then();
            } catch (JsonProcessingException e) {
                return Mono.error(e);
            }
        });
    }
}
