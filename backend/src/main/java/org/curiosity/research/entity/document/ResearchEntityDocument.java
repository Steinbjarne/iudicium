package org.curiosity.research.entity.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "research_entities")
public record ResearchEntityDocument(
        @Id
        String id,
        String canonicalName,
        String displayName,
        String domain,
        String type,
        String description,
        Map<String, Object> domainSpecificProfiles,
        String createdAt,
        String updatedAt
) {
}