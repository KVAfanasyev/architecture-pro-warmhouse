package com.smarthome.legacyadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceConfiguration {

    @JsonProperty("id")
    private String id;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("config_version")
    private String configVersion;

    @JsonProperty("config_data")
    private Object configData;

    @JsonProperty("applied_by")
    private String appliedBy;

    @JsonProperty("status")
    private String status = "PENDING";

    // Конструкторы
    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String deviceId, String configVersion, Object configData, String appliedBy) {
        this.deviceId = deviceId;
        this.configVersion = configVersion;
        this.configData = configData;
        this.appliedBy = appliedBy;
    }

    // Дополнительные методы
    public void markAsApplied() {
        this.status = "APPLIED";
    }

    public void markAsFailed() {
        this.status = "FAILED";
    }

    public boolean isPending() {
        return "PENDING".equals(this.status);
    }

    @Override
    public String toString() {
        return "DeviceConfiguration{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", configVersion='" + configVersion + '\'' +
                ", configData=" + configData +
                ", appliedBy='" + appliedBy + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
