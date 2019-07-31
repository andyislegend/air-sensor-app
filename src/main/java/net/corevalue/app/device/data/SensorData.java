package net.corevalue.app.device.data;

import lombok.Data;
import net.corevalue.app.constant.PayloadField;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SensorData {
    private PayloadField title;
    private Map<PayloadField, String> data;

    public SensorData() {
        data = new LinkedHashMap<>();
    }

    public void putData(PayloadField title, String value) {
        data.put(title, value);
    }
}
