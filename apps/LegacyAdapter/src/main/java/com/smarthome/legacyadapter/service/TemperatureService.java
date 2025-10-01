package com.smarthome.legacyadapter.service;

import com.smarthome.legacyadapter.dto.TemperatureResponse;
import com.smarthome.legacyadapter.messaging.MessageDeviceConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureService {

    @Autowired
    private MessageDeviceConfiguration messageDeviceConfiguration;

    /**
     * Fetches temperature data for a specific location
     */
    public Mono<TemperatureResponse> getTemperature(String location) {
        return Mono.fromCallable(() -> {
            double temperature =  Math.random() * 100.0;
            return new TemperatureResponse(temperature,location);
        }).subscribeOn(Schedulers.boundedElastic());

    }

    /**
     * Fetches temperature data for a specific sensor ID
     */
    public Mono<TemperatureResponse> getTemperatureByID(String sensorId) {
        return Mono.fromCallable(() -> {
            double temperature =  Math.random() * 100.0;
            return new TemperatureResponse(temperature, LocalDateTime.now(), sensorId);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public TemperatureResponse setTemperatureByID(String sensorId, Double temperature) {
        messageDeviceConfiguration.publishTemperatureConfiguration(sensorId,"v1.1","Legacy",temperature.intValue());

        return new TemperatureResponse(temperature, LocalDateTime.now(), sensorId);
    }

}