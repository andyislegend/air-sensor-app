package net.corevalue.app.service.impl;

import net.corevalue.app.device.Device;
import net.corevalue.app.device.DeviceOptions;
import net.corevalue.app.service.Client;
import net.corevalue.app.util.Cryptographer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Singleton
public class DefaultClient implements Client<Device> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultClient.class);
    private static final Lock MONITOR = new ReentrantLock();

    private MqttClient client;
    private String mqttTelemetryTopic;
    private MqttConnectOptions connectionOptions;
    private DeviceOptions deviceOptions;
    private DateTime tokenReceivingTime;
    private boolean connected;


    @Override
    public void sendMessage(MqttMessage message) throws Exception {
        MONITOR.lock();
        if (isTokenRefreshNeed()) {
            if (isConnected()) {
                disconnect();
            }
            setToken(connectionOptions, deviceOptions);
        }
        connect();
        client.publish(mqttTelemetryTopic, message);
        disconnect();
        MONITOR.unlock();
    }

    @Override
    public void setCallBack(Device device) {
        client.setCallback(device);
    }

    @Override
    public void connect() throws MqttException {
        client.connect(connectionOptions);
        connected = true;
    }

    @Override
    public void disconnect() throws MqttException {
        client.disconnect();
        connected = false;
    }


    @Override
    public void init(DeviceOptions deviceOptions) {
        this.deviceOptions = deviceOptions;
        mqttTelemetryTopic = String.format("/devices/%s/events", deviceOptions.getGatewayId());
        String mqttServerAddress = String.format("ssl://%s:%s", deviceOptions.getMqttBridgeHostname(),
                deviceOptions.getMqttBridgePort());
        String mqttClientId = String.format("projects/%s/locations/%s/registries/%s/devices/%s",
                deviceOptions.getProjectId(), deviceOptions.getCloudRegion(), deviceOptions.getRegistryId(),
                deviceOptions.getGatewayId());
        connectionOptions = getConnectionProperties(deviceOptions);
        try {
            client = new MqttClient(mqttServerAddress, mqttClientId, new MemoryPersistence());
        } catch (MqttException e) {
            LOGGER.error("Can't init client: " + e);
        }
    }

    @Override
    public boolean isTokenRefreshNeed() {
        long secsSinceRefresh = ((new DateTime()).getMillis() - tokenReceivingTime.getMillis()) / 1000;
        return secsSinceRefresh > (Cryptographer.TOKEN_EXPIRE_MINS * 60);
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    private MqttConnectOptions getConnectionProperties(DeviceOptions deviceOptions) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        Properties sslProps = new Properties();
        sslProps.setProperty("com.ibm.ssl.protocol", "TLSv1.2");
        connectOptions.setSSLProperties(sslProps);
        connectOptions.setUserName("unused");
        try {
            setToken(connectOptions, deviceOptions);
        } catch (Exception e) {
            LOGGER.error("Can't set password: " + e);
        }
        return connectOptions;
    }

    private void setToken(MqttConnectOptions connectOptions, DeviceOptions deviceOptions) throws Exception {
        connectOptions.setPassword(Cryptographer.createRSAToken(deviceOptions.getProjectId(),
                deviceOptions.getPrivateKeyFile()).toCharArray());
        tokenReceivingTime = new DateTime();
    }
}
