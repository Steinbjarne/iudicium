package org.curiosity.api.health;

import org.curiosity.api.health.record.HealthComponentsResponse;
import org.curiosity.api.health.record.HealthPingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/ping")
    public ResponseEntity<HealthPingResponse> ping() {
        return ResponseEntity.ok(
                new HealthPingResponse(
                        "OK",
                        Instant.now().toString()
                )
        );
    }

    @GetMapping("/components")
    public ResponseEntity<HealthComponentsResponse> components() {
        var response = healthService.getComponents();

        var databaseOk = "OK".equals(response.database().status());

        return databaseOk
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}