package com.jmeta.send;

import lombok.Builder;

@Builder
public record MessageResponse(int statusCode, String responseBody) {
}
