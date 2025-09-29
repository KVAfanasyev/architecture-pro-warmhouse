package com.smarthome.devicemanagement.messaging;

import com.smarthome.devicemanagement.entity.Device;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;



@Component
@RequiredArgsConstructor
@Slf4j
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String STATUS_EXCHANGE = "device.status.exchange";

    public void publishStatusChange(Device device) {
        try {
            String message = objectMapper.writeValueAsString(createStatusEvent(device));
            rabbitTemplate.convertAndSend(STATUS_EXCHANGE, "device.status." + device.getId(), message);
            log.debug("Status change published for device: {}", device.getId());
        } catch (Exception e) {
            log.error("Failed to publish status change", e);
        }
    }


    private Map<String, Object> createStatusEvent(Device device) {
        Map<String, Object> event = new HashMap<>();
        event.put("deviceId", device.getId());
        event.put("status", device.getStatus().name());
        event.put("online", device.isOnline());
        event.put("timestamp", LocalDateTime.now());
        event.put("ipAddress", device.getIpAddress());
        return event;
    }
}