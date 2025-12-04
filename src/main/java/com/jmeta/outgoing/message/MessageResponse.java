package com.jmeta.outgoing.message;

import lombok.Builder;

@Builder
public record MessageResponse(int statusCode, String responseBody) {
}
