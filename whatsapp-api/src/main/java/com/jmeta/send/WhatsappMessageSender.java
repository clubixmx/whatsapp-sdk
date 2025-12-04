package com.jmeta.send;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class WhatsappMessageSender implements MessageSender{
    private final String metaUrl;
    private final String token;

    private final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    private final WebClient webClient;

    public WhatsappMessageSender(String metaUrl, String token) {
        this.metaUrl = metaUrl;
        this.token = token;
        this.webClient = WebClient.builder()
                .baseUrl(metaUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    // Reactive send: returns Mono\<MessageResponse\>
    public Mono<MessageResponse> send (WhatsappMessage whatsappMessage) {
        return Mono.defer(() -> {
            log.info("Sending WhatsappMessage:{}",whatsappMessage.toString());
            try {
                String json = mapper.writeValueAsString(whatsappMessage);
                return webClient.post()
                        .bodyValue(json)
                        .exchangeToMono(resp ->
                                resp.bodyToMono(String.class).defaultIfEmpty("")
                                        .map(body -> new MessageResponse(resp.statusCode().value(), body))
                        );
            } catch (JsonProcessingException e) {
                return Mono.error(e);
            }
        });
    }
}
