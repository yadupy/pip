package com.accenture.pip.customermanagement.controller;

import com.accenture.pip.customermanagement.dto.Health;
import com.accenture.pip.customermanagement.dto.HealthStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    @Operation(
            summary = "checks system health",
            description = "checks system health")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public ResponseEntity<Health> getHealth(){
        log.info("GET Method called to get current health of the service");
        Health health = new Health();
        health.setHealthStatus(HealthStatus.UP);
        return ResponseEntity.ok(health);
    }
}
