package org.curiosity.api.health.record;

public record DatabaseComponent(
        String status,
        String timestamp,
        long responseTimeMs
) {}
