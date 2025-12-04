package com.jmeta.api.processor.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class IncomingMessageMapper {
    public static Optional<IncomingMessage> map(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode entry0 = root.path("entry").get(0);
        JsonNode changes0 = entry0.path("changes").get(0).path("value");

        // Ignore delivery/read/status callbacks
        if (changes0.has("statuses")) {
            return Optional.empty();
        }

        // Ensure there is a messages array
        JsonNode messagesNode = changes0.path("messages");
        if (messagesNode.isMissingNode() || !messagesNode.isArray() || messagesNode.size() == 0) {
            return Optional.empty();
        }

        // profile fields
        JsonNode contact0 = changes0.path("contacts").get(0);
        String name = contact0.path("profile").path("name").asText();
        String waIdRaw = contact0.path("wa_id").asText();
        // Normalize Mexican numbers: WhatsApp uses 521 (old mobile format); convert to 52.
        String waId = (waIdRaw.startsWith("521") && waIdRaw.length() > 3)
                ? "52" + waIdRaw.substring(3)
                : waIdRaw;

        // message fields
        JsonNode message0 = messagesNode.get(0);
        String type = message0.path("type").asText();

        Profile profile = new Profile(name, waId);
        return switch (type) {
            case "text" -> Optional.of(mapTextMessage(message0, profile));
            default -> Optional.empty(); // unsupported types ignored
        };
    }

    private static IncomingTextMessage mapTextMessage(JsonNode messageNode, Profile profile) {
        String type = messageNode.path("type").asText();
        String text = messageNode.path("text").path("body").asText();
        return new IncomingTextMessage(profile, type, text);
    }
}
