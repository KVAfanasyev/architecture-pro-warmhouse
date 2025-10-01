package com.smarthome.legacyadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureDeviceConfiguration extends DeviceConfiguration {


    private Integer temperature=0;


    public TemperatureDeviceConfiguration() {
        super();
        initializeConfigData();
    }

    public TemperatureDeviceConfiguration(String deviceId, String configVersion, String appliedBy,
                                          Integer temperature) {
        super(deviceId, configVersion, null, appliedBy);
        this.temperature = temperature;
        initializeConfigData();
    }

    private void initializeConfigData() {
        TemperatureConfigData configData = new TemperatureConfigData();
        configData.setTemperature(this.temperature);
        setConfigData(configData);
    }

    @Getter
    @Setter
    public static class TemperatureConfigData {

        @JsonProperty("temperature")
        private Integer temperature;

    }


    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
        updateConfigData();
    }


    private void updateConfigData() {
        if (getConfigData() instanceof TemperatureConfigData) {
            TemperatureConfigData configData = (TemperatureConfigData) getConfigData();
            configData.setTemperature(this.temperature);
        } else {
            initializeConfigData();
        }
    }
}