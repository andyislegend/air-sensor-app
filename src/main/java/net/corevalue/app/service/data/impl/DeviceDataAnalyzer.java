package net.corevalue.app.service.data.impl;

import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.service.data.DataAnalyzer;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class DeviceDataAnalyzer implements DataAnalyzer<Device, MqttMessage> {
    @Override
    public MqttMessage getDeviceData(Device device, SensorType... sensorTypes) {
        JSONObject payload = new JSONObject();
        Arrays.asList(sensorTypes).forEach(sensorType -> fillPayload(device.readDeviceData(sensorType), payload));
        MqttMessage message = new MqttMessage(payload.toString().getBytes());
        message.setQos(1);
        return message;
    }

    private void fillPayload(SensorData sensorData, JSONObject payload) {
        payload.put(sensorData.getTitle().getValue(), sensorData.getData());
    }
}
