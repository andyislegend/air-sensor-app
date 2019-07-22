package net.corevalue.app.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeviceType {
    RASPBERRY("Raspberry Pi");

    private String value;
}
