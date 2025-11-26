package com.jmeta.api.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class HealthCheckHandlerTest {

    private WebTestClient newClient() {
        HealthCheckHandler handler = new HealthCheckHandler("cashback");
        RouterFunction<ServerResponse> rf = route(GET("/health"), handler::healthCheck);
        return WebTestClient.bindToRouterFunction(rf).build();
    }

    @Test
    void healthCheck_success() {
        newClient().get()
                .uri("/health?hub.mode=subscribe&hub.verify_token=cashback&hub.challenge=12345")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
                .expectBody(String.class).isEqualTo("12345");
    }

    @Test
    void healthCheck_forbidden_wrong_token() {
        newClient().get()
                .uri("/health?hub.mode=subscribe&hub.verify_token=wrong&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }
    @Test
    void healthCheck_forbidden_missing_token() {
        newClient().get()
                .uri("/health?hub.mode=subscribe&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }

    @Test
    void healthCheck_forbidden_missing_mode() {
        newClient().get()
                .uri("/health?hub.verify_token=cashback&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }

    @Test
    void healthCheck_forbidden_wrong_mode() {
        newClient().get()
                .uri("/health?hub.mode=unsubscribe&hub.verify_token=cashback&hub.challenge=abc")
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(String.class).isEqualTo("Forbidden");
    }
}
