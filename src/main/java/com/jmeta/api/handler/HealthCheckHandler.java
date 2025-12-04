package com.jmeta.api.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HealthCheckHandler {

    private final String veryfyToken;

    public Mono<ServerResponse> healthCheck(ServerRequest request) {
        String mode = request.queryParam("hub.mode").orElse("");
        String token = request.queryParam("hub.verify_token").orElse("");
        String challenge = request.queryParam("hub.challenge").orElse("");
        if ("subscribe".equals(mode) && veryfyToken.equals(token)) {
            return ServerResponse.ok().bodyValue(challenge);
        } else {
            return ServerResponse.status(403).bodyValue("Forbidden");
        }
    }
}
