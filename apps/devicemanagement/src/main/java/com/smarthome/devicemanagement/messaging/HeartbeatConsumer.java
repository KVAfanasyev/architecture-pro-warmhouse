package com.smarthome.devicemanagement.messaging;

import com.smarthome.devicemanagement.dto.HeartbeatEvent;
import com.smarthome.devicemanagement.service.DeviceStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeartbeatConsumer {

    private final DeviceStatusService deviceStatusService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queues.heartbeat:device.heartbeat.queue}" )
    public void consumeHeartbeat(String message) {
        try {
            HeartbeatEvent event = objectMapper.readValue(message, HeartbeatEvent.class);
            deviceStatusService.processHeartbeat(event);
        } catch (Exception e) {
            log.error("Failed to process heartbeat message", e);
        }
    }
}