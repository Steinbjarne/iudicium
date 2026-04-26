package org.curiosity.research.entity.record;

import java.util.Map;

public record CreateEntityRequest(
        Identity identity,
        String domain,
        String type,
        String description,
        Map<String, Object> domainSpecificProfiles
) {
    public record Identity(
            String canonicalName,
            String displayName
    ) {
    }
}