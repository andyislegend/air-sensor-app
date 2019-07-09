package net.corevalue.app.device.data;

import lombok.Data;
import net.corevalue.app.constant.PayloadField;
import net.corevalue.app.constant.SensorType;

import java.util.HashMap;
import java.util.Map;

@Data
public class SensorData {
    private SensorType sensorType;
    private Map<PayloadField, String> data;

    public SensorData() {
        data = new HashMap<>();
    }

    public void putData(PayloadField title, String value) {
        data.put(title, value);
    }
}
