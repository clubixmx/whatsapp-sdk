package com.jmeta.outgoing.message;

import lombok.Builder;

@Builder
public record TextContent (boolean preview_url, String body) {}