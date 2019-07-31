package net.corevalue.app.service.data;

import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface DataAnalyzer<T extends Device, R extends MqttMessage> {

    R getDeviceData(T device, SensorType... sensorTypes);
}
