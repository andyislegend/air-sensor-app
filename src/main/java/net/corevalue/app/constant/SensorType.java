package net.corevalue.app.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SensorType {
    CO2_SENSOR("Co2 Sensor"), HUMIDITY_SENSOR("Humidity Sensor"),
    TEMPERATURE_SENSOR("Temperature Sensor"), LIGHT_SENSOR("Light Sensor");

    private String value;
}
