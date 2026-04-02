package org.curiosity.api.health.record;

public record HealthPingResponse(
        String ping,
        String time
) {}