package net.corevalue.app.device;

import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.device.sensor.Sensor;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import java.util.Map;

public interface Device extends MqttCallback {

    SensorData readDeviceData(SensorType sensorType);

    void setSensorMap(Map<SensorType, Sensor> sensorMap);

    Map<SensorType, Sensor> getSensorMap();

    Sensor getSensor(SensorType sensorType);

    boolean isEnabled();
}
