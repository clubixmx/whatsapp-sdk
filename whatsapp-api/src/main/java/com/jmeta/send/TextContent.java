package com.jmeta.send;

import lombok.Builder;

@Builder
public record TextContent (boolean preview_url, String body) {}