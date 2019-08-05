package net.corevalue.app.device.data;

import lombok.Getter;
import lombok.Setter;
import net.corevalue.app.constant.PayloadField;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class SensorData {
    @Setter
    private PayloadField title;
    private Map<PayloadField, String> data;

    public SensorData() {
        data = new LinkedHashMap<>();
    }

    public void putData(PayloadField title, String value) {
        data.put(title, value);
    }
}
