package com.jmeta.api.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(prefix = "whatsapp.outgoing")
@RequiredArgsConstructor
public class WhatsappProperties {
    private final String metaUrl;
    private final String token;
}
