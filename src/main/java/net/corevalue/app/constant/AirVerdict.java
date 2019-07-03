package net.corevalue.app.constant;

import lombok.Getter;

@Getter
public enum AirVerdict {
    CO2_EXCEED("CO2 exceed limits. Please, open the window"),
    CO2_NORMAL("CO2 within acceptable limits.");

    private String description;

    AirVerdict(String description) {
        this.description = description;
    }
}
