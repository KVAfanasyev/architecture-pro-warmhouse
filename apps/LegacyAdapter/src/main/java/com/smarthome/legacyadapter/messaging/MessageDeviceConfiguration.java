package com.smarthome.legacyadapter.messaging;

import com.smarthome.legacyadapter.dto.DeviceConfiguration;
import com.smarthome.legacyadapter.dto.TemperatureDeviceConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageDeviceConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EXCHANGE_NAME = "device.configuration.exchange";
    private static final String ROUTING_KEY = "device.configuration.static";

    public void publishConfiguration(DeviceConfiguration configuration) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(configuration);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, jsonMessage);
            System.out.println("Message sent to RabbitMQ: " + jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing configuration to JSON", e);
        }
    }

    public void publishTemperatureConfiguration(String deviceId, String configVersion, String appliedBy,
                                                Integer temperature) {

        TemperatureDeviceConfiguration config = new TemperatureDeviceConfiguration(
                deviceId, configVersion, appliedBy, temperature );
        publishConfiguration(config);
    }
}