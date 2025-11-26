package com.jmeta.api.config;

import com.jmeta.api.handler.HealthCheckHandler;
import com.jmeta.api.handler.IncomingMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(HealthCheckHandler healthCheckHandler, IncomingMessageHandler incomingMessageHandler) {
        return route(GET("/hook"), healthCheckHandler::healthCheck)
                .andRoute(POST("/hook").and(contentType(MediaType.APPLICATION_JSON)),incomingMessageHandler::receive);
    }
}
