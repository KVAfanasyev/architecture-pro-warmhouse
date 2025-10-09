package com.smarthome.devicemanagement.service;

import com.smarthome.devicemanagement.dto.DeviceStatusDto;
import com.smarthome.devicemanagement.dto.HeartbeatEvent;
import com.smarthome.devicemanagement.entity.Device;
import com.smarthome.devicemanagement.messaging.MessagePublisher;
import com.smarthome.devicemanagement.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.smarthome.devicemanagement.exception.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceStatusService {

    private final DeviceRepository deviceRepository;
    private final MessagePublisher messagePublisher;

    @Transactional
    public void processHeartbeat(HeartbeatEvent event) {
        deviceRepository.findById(event.getDeviceId()).ifPresentOrElse(
                device -> {
                    device.setLastSeen(LocalDateTime.now());
                    device.setStatus(Device.DeviceStatus.ONLINE);
                    device.setIpAddress(event.getIpAddress());

                    if (device.getMetadata() != null && event.getMetrics() != null) {
                        device.getMetadata().putAll(event.getMetrics());
                    }

                    deviceRepository.save(device);
                    messagePublisher.publishStatusChange(device);
                    log.info("Heartbeat processed for device: {}", event.getDeviceId());
                },
                () -> log.warn("Unknown device heartbeat: {}", event.getDeviceId())
        );
    }

    @Transactional(readOnly = true)
    public DeviceStatusDto getDeviceStatus(String deviceId) {
        return deviceRepository.findById(deviceId)
                .map(this::convertToDto)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found: " + deviceId));
    }

    @Transactional
    public void updateDeviceStatus(String deviceId, Device.DeviceStatus status) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found: " + deviceId));

        device.setStatus(status);
        deviceRepository.save(device);
        messagePublisher.publishStatusChange(device);
    }

    @Scheduled(fixedRate = 300000) // 5 minutes
    @Transactional
    public void checkOfflineDevices() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        deviceRepository.markOfflineDevices(threshold);
        log.info("Offline devices check completed");
    }

    private DeviceStatusDto convertToDto(Device device) {
        DeviceStatusDto dto = new DeviceStatusDto();
        dto.setDeviceId(device.getId());
        dto.setStatus(device.getStatus().name());
        dto.setOnline(device.isOnline());
        dto.setLastSeen(device.getLastSeen());
        dto.setIpAddress(device.getIpAddress());
        return dto;
    }
}