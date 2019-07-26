package net.corevalue.app.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayloadField {
    CO2_SENSOR("co2Sensor"), CO2_AIR_VERDICT("co2LevelVerdict"), CO2_QUANTITY("co2Quantity");

    private String value;

    @Override
    public String toString() {
        return value;
    }
}
