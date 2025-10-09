package com.smarthome.devicemanagement.controller;

import com.smarthome.devicemanagement.dto.DeviceStatusDto;
import com.smarthome.devicemanagement.entity.Device;
import com.smarthome.devicemanagement.service.DeviceStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
@Tag(name = "Device Status Management", description = "API для управления статусом устройств")
public class DeviceStatusController {

    private final DeviceStatusService deviceStatusService;

    @GetMapping("/{deviceId}/status")
    @Operation(summary = "Получить статус устройства",
            description = "Возвращает текущий статус и информацию об устройстве")
    public ResponseEntity<DeviceStatusDto> getDeviceStatus(
            @Parameter(description = "ID устройства") @PathVariable String deviceId) {
        DeviceStatusDto status = deviceStatusService.getDeviceStatus(deviceId);
        return ResponseEntity.ok(status);
    }

    @PutMapping("/{deviceId}/status")
    @Operation(summary = "Обновить статус устройства",
            description = "Позволяет вручную установить статус устройства")
    public ResponseEntity<Void> updateDeviceStatus(
            @Parameter(description = "ID устройства") @PathVariable String deviceId,
            @Parameter(description = "Новый статус") @RequestParam Device.DeviceStatus status) {
        deviceStatusService.updateDeviceStatus(deviceId, status);
        return ResponseEntity.ok().build();
    }

}