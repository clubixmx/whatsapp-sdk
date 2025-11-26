package com.jmeta.api.processor.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IncomingMessageMapper {
    public static IncomingMessage map(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode entry0 = root.path("entry").get(0);
        JsonNode changes0 = entry0.path("changes").get(0).path("value");

        // profile fields
        JsonNode contact0 = changes0.path("contacts").get(0);
        String name = contact0.path("profile").path("name").asText();
        String waIdRaw = contact0.path("wa_id").asText();
        // Normalize Mexican numbers: WhatsApp uses 521 (old mobile format); convert to 52.
        String waId = (waIdRaw.startsWith("521") && waIdRaw.length() > 3)
                ? "52" + waIdRaw.substring(3)
                : waIdRaw;

        // message fields
        JsonNode message0 = changes0.path("messages").get(0);
        String type = message0.path("type").asText();
        String text = message0.path("text").path("body").asText();

        Profile profile = new Profile(name, waId);
        switch (type) {
            case "text" -> {
                return mapTextMessage(message0, profile);
            }
            default -> throw new IllegalArgumentException("Unsupported message type: " + type);
        }
    }

    private static IncomingTextMessage mapTextMessage(JsonNode messageNode, Profile profile) {
        String type = messageNode.path("type").asText();
        String text = messageNode.path("text").path("body").asText();
        return new IncomingTextMessage(profile, type, text);
    }
}
