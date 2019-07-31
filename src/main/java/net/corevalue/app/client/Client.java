package net.corevalue.app.client;

import net.corevalue.app.device.Device;
import net.corevalue.app.util.ConnectionArguments;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface Client<T extends Device> {

    void initConnection(ConnectionArguments connectionArguments) throws Exception;

    void connect() throws MqttException;

    void disconnect() throws MqttException;

    void sendMessage(MqttMessage message) throws Exception;

    void setCallBack(T device);

    boolean isTokenRefreshNeed();

    boolean isConnected();
}
