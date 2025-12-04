package com.jmeta.api.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class IncomingMessageHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(IncomingMessageHandlerTest.class);
    private WebTestClient client;

    @BeforeEach
    void setUp() {

        // SUT to be implemented: IncomingMessageHandler#receive(ServerRequest)
        IncomingMessageHandler handler = new IncomingMessageHandler(Map.of(
                "text", new com.jmeta.api.processor.MessageProcessor(){
                    @Override
                    public void process(com.jmeta.api.processor.message.IncomingMessage incomingMessage) {
                        log.info("Mock processor received message: {}", ((com.jmeta.api.processor.message.IncomingTextMessage)incomingMessage).text());
                    }
                }
        ));

        RouterFunction<ServerResponse> routes =
                route(POST("/incoming").and(contentType(MediaType.APPLICATION_JSON)), handler::receive);

        client = WebTestClient.bindToRouterFunction(routes).build();
    }

    @Test
    void receivesSampleMessage_andResponds200() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/contracts/incoming/hello_world.json")) {
            assertNotNull(is, "resource not found");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            client.post()
                    .uri("/incoming")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .exchange()
                    .expectStatus().isOk();
        }
    }
}
