package com.jmeta.api.config;


import com.jmeta.api.handler.HealthCheckHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class RouterTest {
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        // Arrange: wire the router to the handler
        HealthCheckHandler handler = new HealthCheckHandler("cashback");
        RouterFunction<ServerResponse> routes = route(GET("/hook"), handler::healthCheck);

        // Bind a client directly to the RouterFunction (no full Spring context)
        client = WebTestClient.bindToRouterFunction(routes).build();
    }

    @Test
    void health_success() {
        client.get()
                .uri("/hook?hub.mode=subscribe&hub.verify_token=cashback&hub.challenge=12345")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
                .expectBody(String.class).isEqualTo("12345");
    }

    @Test
    void health_forbidden_wrong_token() {
        client.get()
                .uri("/hook?hub.mode=subscribe&hub.verify_token=wrong&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }

    @Test
    void health_forbidden_missing_token() {
        client.get()
                .uri("/hook?hub.mode=subscribe&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }

    @Test
    void health_forbidden_missing_mode() {
        client.get()
                .uri("/hook?hub.verify_token=cashback&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }

    @Test
    void health_forbidden_wrong_mode() {
        client.get()
                .uri("/hook?hub.mode=unsubscribe&hub.verify_token=cashback&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }
}
