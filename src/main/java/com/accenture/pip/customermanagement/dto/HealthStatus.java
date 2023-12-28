package com.accenture.pip.customermanagement.dto;

public enum HealthStatus {
    UP("UP"),
    DOWN("DOWN");

    public final String status;
    HealthStatus(String status) {
        this.status = status;
    }
}
