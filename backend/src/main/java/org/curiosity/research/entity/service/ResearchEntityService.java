package org.curiosity.research.entity.service;

import org.curiosity.research.entity.document.ResearchEntityDocument;
import org.curiosity.research.entity.record.CreateEntityRequest;
import org.curiosity.research.entity.record.CreateEntityResponse;
import org.curiosity.research.entity.repository.ResearchEntityRepository;
import org.curiosity.research.entity.validator.CreateEntityRequestValidator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ResearchEntityService {

    private final ResearchEntityRepository repository;
    private final CreateEntityRequestValidator validator;

    public ResearchEntityService(
            ResearchEntityRepository repository,
            CreateEntityRequestValidator validator
    ) {
        this.repository = repository;
        this.validator = validator;
    }

    public CreateEntityResponse create(CreateEntityRequest request) {
        validator.validate(request);

        var now = Instant.now().toString();
        var entityId = UUID.randomUUID().toString();

        var document = new ResearchEntityDocument(
                entityId,
                request.identity().canonicalName(),
                request.identity().displayName(),
                request.domain(),
                request.type(),
                request.description(),
                request.domainSpecificProfiles(),
                now,
                now
        );

        repository.save(document);

        return new CreateEntityResponse(entityId, now);
    }
}