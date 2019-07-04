package net.corevalue.app.service;

import net.corevalue.app.device.Device;
import net.corevalue.app.device.DeviceOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface Client<T extends Device> {

    void initConnection(DeviceOptions options);

    void connect() throws MqttException;

    void disconnect() throws MqttException;

    void sendMessage(MqttMessage message) throws Exception;

    void setCallBack(T device);

    boolean isTokenRefreshNeed();

    boolean isConnected();
}
