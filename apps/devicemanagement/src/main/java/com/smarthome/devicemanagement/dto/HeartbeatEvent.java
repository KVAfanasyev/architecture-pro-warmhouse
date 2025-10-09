package com.smarthome.devicemanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class HeartbeatEvent {
    private String deviceId;
    private LocalDateTime timestamp;
    private String ipAddress;
    private Map<String, Object> metrics;
}