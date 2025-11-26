package com.jmeta.api.handler;

import com.jmeta.api.processor.MessageProcessor;
import com.jmeta.api.processor.message.IncomingMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomingMessageHandler {

    private final Map<String,MessageProcessor> messageProcessorMap;

    public Mono<ServerResponse> receive(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(String.class)
                .flatMap(body -> Mono.fromCallable(() -> IncomingMessageMapper.map(body)))
                .doOnNext(incomingMessage -> {
                    // fire-and-forget processing on a boundedElastic scheduler
                    Mono.fromRunnable(() -> {
                                String type = incomingMessage.type();
                                MessageProcessor processor = messageProcessorMap.get(type);
                                if (processor == null) {
                                    log.warn("No MessageProcessor found for type '{}'", type);
                                    return;
                                }
                                processor.process(incomingMessage);
                            })
                            .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())
                            .subscribe();
                })
                // immediately acknowledge after mapping succeeds
                .flatMap(msg -> ServerResponse.ok().build())
                .onErrorResume(e -> {
                    log.error("Mapping error", e);
                    return ServerResponse.badRequest().bodyValue("Invalid incoming message");
                });
    }
}
