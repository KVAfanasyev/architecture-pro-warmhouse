package com.smarthome.devicemanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO статуса устройства")
public class DeviceStatusDto {
    @Schema(description = "ID устройства", example = "device-123")
    private String deviceId;

    @Schema(description = "Статус устройства", example = "ONLINE")
    private String status;

    @Schema(description = "Флаг онлайн статуса")
    private boolean online;

    @Schema(description = "Время последней активности")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSeen;

    @Schema(description = "IP адрес устройства", example = "192.168.1.100")
    private String ipAddress;
}