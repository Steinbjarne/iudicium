package org.curiosity.research.entity.validator;

import org.curiosity.api.error.BadRequestException;
import org.curiosity.research.entity.record.CreateEntityRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateEntityRequestValidator {

    public void validate(CreateEntityRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }

        if (request.identity() == null) {
            throw new BadRequestException("identity is required");
        }

        if (isBlank(request.identity().canonicalName())) {
            throw new BadRequestException("identity.canonicalName is required");
        }

        if (isBlank(request.identity().displayName())) {
            throw new BadRequestException("identity.displayName is required");
        }

        if (isBlank(request.domain())) {
            throw new BadRequestException("domain is required");
        }

        if (isBlank(request.type())) {
            throw new BadRequestException("type is required");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}