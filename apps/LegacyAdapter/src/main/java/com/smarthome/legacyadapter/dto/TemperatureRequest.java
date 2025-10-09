package com.smarthome.legacyadapter.dto;

import jakarta.validation.constraints.NotNull;

public class TemperatureRequest {

    @NotNull(message = "deviceId не может быть пустым")
    private String deviceId;

    @NotNull(message = "temperature не может быть пустым")
    private Integer temperature;


    public TemperatureRequest() {}

    public TemperatureRequest(String deviceId, Integer temperature) {
        this.deviceId = deviceId;
        this.temperature = temperature;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TemperatureRequest{" +
                "deviceId='" + deviceId + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}