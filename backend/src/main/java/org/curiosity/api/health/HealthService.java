package org.curiosity.api.health;

import java.time.Instant;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.curiosity.api.health.record.AppComponent;
import org.curiosity.api.health.record.DatabaseComponent;
import org.curiosity.api.health.record.HealthComponentsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final MongoClient mongoClient;

    public HealthService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public HealthComponentsResponse getComponents() {
        var timestamp = Instant.now().toString();

        var app = new AppComponent(
                "OK",
                timestamp
        );

        var database = getDatabaseComponent(timestamp);

        return new HealthComponentsResponse(app, database);
    }

    private DatabaseComponent getDatabaseComponent(String timestamp) {
        long start = System.nanoTime();

        try {
            mongoClient
                    .getDatabase("admin")
                    .runCommand(new Document("ping", 1));

            long elapsedNanos = System.nanoTime() - start;
            long responseTimeMs = elapsedNanos / 1_000_000;

            return new DatabaseComponent(
                    "OK",
                    timestamp,
                    responseTimeMs
            );
        } catch (Exception ex) {
            long elapsedNanos = System.nanoTime() - start;
            long responseTimeMs = elapsedNanos / 1_000_000;

            log.error("Mongo server ping failed. timestamp={}, responseTimeMs={}", timestamp, responseTimeMs, ex);

            return new DatabaseComponent(
                    "DOWN",
                    timestamp,
                    responseTimeMs
            );
        }
    }
}