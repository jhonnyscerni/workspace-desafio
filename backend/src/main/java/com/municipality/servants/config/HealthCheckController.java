package com.municipality.servants.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Health Check Controller for Docker container monitoring
 * Provides endpoints for liveness and readiness probes
 */
@Slf4j
@RestController
public class HealthCheckController {

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    private BuildProperties buildProperties;

    /**
     * Basic health check endpoint
     * Used by Docker HEALTHCHECK directive
     *
     * @return HTTP 200 if application is running
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", getCurrentTimestamp());
        health.put("application", "Servants Management System");

        return ResponseEntity.ok(health);
    }

    /**
     * Readiness probe endpoint
     * Checks if application is ready to accept traffic
     * Validates database connectivity
     *
     * @return HTTP 200 if ready, HTTP 503 if not ready
     */
    @GetMapping("/ready")
    public ResponseEntity<Map<String, Object>> ready() {
        Map<String, Object> readiness = new HashMap<>();

        try {
            // Check database connectivity
            boolean dbConnected = checkDatabaseConnection();

            if (dbConnected) {
                readiness.put("status", "READY");
                readiness.put("timestamp", getCurrentTimestamp());
                readiness.put("database", "CONNECTED");
                readiness.put("application", "Servants Management System");

                if (buildProperties != null) {
                    readiness.put("version", buildProperties.getVersion());
                }

                return ResponseEntity.ok(readiness);
            } else {
                readiness.put("status", "NOT_READY");
                readiness.put("timestamp", getCurrentTimestamp());
                readiness.put("database", "DISCONNECTED");

                log.warn("Readiness check failed: Database connection unavailable");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(readiness);
            }

        } catch (Exception e) {
            readiness.put("status", "ERROR");
            readiness.put("timestamp", getCurrentTimestamp());
            readiness.put("error", e.getMessage());

            log.error("Readiness check failed with exception", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(readiness);
        }
    }

    /**
     * Detailed application info endpoint
     *
     * @return Application metadata
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> info = new HashMap<>();

        info.put("application", "Servants Management System");
        info.put("description", "Municipal Public Servants Management System");
        info.put("timestamp", getCurrentTimestamp());

        if (buildProperties != null) {
            info.put("version", buildProperties.getVersion());
            info.put("name", buildProperties.getName());
            info.put("group", buildProperties.getGroup());
            info.put("artifact", buildProperties.getArtifact());
        }

        // Runtime information
        Map<String, Object> runtime = new HashMap<>();
        runtime.put("java.version", System.getProperty("java.version"));
        runtime.put("java.vendor", System.getProperty("java.vendor"));
        runtime.put("os.name", System.getProperty("os.name"));
        runtime.put("os.version", System.getProperty("os.version"));
        info.put("runtime", runtime);

        return ResponseEntity.ok(info);
    }

    /**
     * Check database connection
     *
     * @return true if database is accessible, false otherwise
     */
    private boolean checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2); // 2 seconds timeout
        } catch (Exception e) {
            log.error("Database connection check failed", e);
            return false;
        }
    }

    /**
     * Get current timestamp in ISO format
     *
     * @return Formatted timestamp string
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
