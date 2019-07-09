package net.corevalue.app.device;

import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.device.sensor.Sensor;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import java.util.Map;
import java.util.function.Supplier;

public interface Device extends MqttCallback {

    void setReadStrategyMap(Map<SensorType, Supplier<SensorData>> readStrategyMap);

    SensorData readDeviceData(SensorType sensorType);

    Sensor getCo2Sensor();

    Sensor getHumiditySensor();

    Sensor getTemperatureSensor();

    Sensor getLightSensor();
}
