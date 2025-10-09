package com.smarthome.legacyadapter.controller;

import com.smarthome.legacyadapter.dto.TemperatureRequest;
import com.smarthome.legacyadapter.dto.TemperatureResponse;
import com.smarthome.legacyadapter.service.TemperatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/temperature")
@RequiredArgsConstructor
public class TemperatureController {

    private final TemperatureService temperatureService;

    @GetMapping
    public Mono<TemperatureResponse> getTemperature(@RequestParam String location) {
        return temperatureService.getTemperature(location);
    }

    @GetMapping("/{sensorId}")
    public Mono<TemperatureResponse> getTemperatureByID(@PathVariable String sensorId) {
        return temperatureService.getTemperatureByID(sensorId);
    }

    @PostMapping("/submit")
    public Mono<ResponseEntity<TemperatureResponse>> submitTemperature(
            @Valid @RequestBody TemperatureRequest request) {

        return Mono.just(request)
                .map(req -> {
                    // Логика обработки температуры
                    String status = "SUCCESS";
                    String message = String.format("Temperature %d°C from device %s accepted",
                            req.getTemperature(), req.getDeviceId());

                    TemperatureResponse response = temperatureService.setTemperatureByID (req.getDeviceId(),
                            req.getTemperature().doubleValue());

                    return ResponseEntity.ok(response);
                });
    }

}