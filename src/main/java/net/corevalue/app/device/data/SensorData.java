package net.corevalue.app.device.data;

import lombok.Data;
import net.corevalue.app.constant.PayloadField;

import java.util.HashMap;
import java.util.Map;

@Data
public class SensorData {
    private PayloadField title;
    private Map<PayloadField, String> data;

    public SensorData() {
        data = new HashMap<>();
    }

    public void putData(PayloadField title, String value) {
        data.put(title, value);
    }
}
