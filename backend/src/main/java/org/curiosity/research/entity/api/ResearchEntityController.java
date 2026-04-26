package org.curiosity.research.entity.api;

import org.curiosity.research.entity.record.CreateEntityRequest;
import org.curiosity.research.entity.record.CreateEntityResponse;
import org.curiosity.research.entity.service.ResearchEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research/entities")
public class ResearchEntityController {

    private final ResearchEntityService service;

    public ResearchEntityController(ResearchEntityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateEntityResponse> create(@RequestBody CreateEntityRequest request) {
        var response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}