package net.corevalue.app.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayloadField {
    CO2_SENSOR("Co2 sensor"), CO2_AIR_VERDICT("CO2 level verdict"), CO2_QUANTITY("Co2 quantity");

    private String value;
}
