package com.jmeta.incoming.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "whatsapp.hook")
@Data
public class WhatsappHookProperties {
    private String verifyToken;
}
