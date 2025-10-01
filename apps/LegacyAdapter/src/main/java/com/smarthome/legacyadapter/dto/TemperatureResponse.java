// TemperatureResponse.java
package com.smarthome.legacyadapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TemperatureResponse {
    public TemperatureResponse(Double iValue,String ilocation)
    {
        value= iValue;
        location=ilocation;
    }
    public TemperatureResponse(Double iValue,LocalDateTime timestamp, String sensorId)
    {
        value= iValue;
        sensorId=sensorId;
    }
    @JsonProperty("value")
    private Double value;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("location")
    private String location;

    @JsonProperty("status")
    private String status;

    @JsonProperty("sensor_id")
    private String sensorId;

    @JsonProperty("sensor_type")
    private String sensorType;

    @JsonProperty("description")
    private String description;
}