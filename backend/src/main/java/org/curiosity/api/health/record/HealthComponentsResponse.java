package org.curiosity.api.health.record;

public record HealthComponentsResponse(
        AppComponent app,
        DatabaseComponent database
) {}
