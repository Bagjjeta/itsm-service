package com.itsm.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "Endpointy sprawdzające stan działania systemu")
public class HealthCheckController {

    @GetMapping
    @Operation(summary = "Sprawdź status serwera", description = "Zwraca informację, czy serwer aplikacji działa poprawnie.")
    public Object checkHealth() {
        return Map.of(
                "status", "UP",
                "message", "ITSM System is running"
        );
    }
}