package org.curiosity.research.entity.repository;

import org.curiosity.research.entity.document.ResearchEntityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResearchEntityRepository extends MongoRepository<ResearchEntityDocument, String> {
}