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

@Singleton
public class DefaultClient implements Client<Device> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultClient.class);
    private MqttClient client;
    private String mqttTelemetryTopic;
    private MqttConnectOptions connectionOptions;
    private DeviceOptions deviceOptions;
    private DateTime tokenReceivingTime;
    private boolean connected;


    @Override
    public synchronized void sendMessage(MqttMessage message) throws Exception {
        if (isTokenRefreshNeed()) {
            if (isConnected()) {
                disconnect();
            }
            setToken(connectionOptions, deviceOptions);
            LOGGER.info("Refreshed token...");
            connect();
        }
        client.publish(mqttTelemetryTopic, message);
    }

    @Override
    public synchronized void setCallBack(Device device) {
        client.setCallback(device);
    }

    @Override
    public synchronized void connect() throws MqttException {
        client.connect(connectionOptions);
        connected = true;
        LOGGER.info("Connection initiated...");
    }

    @Override
    public synchronized void disconnect() throws MqttException {
        client.disconnect();
        connected = false;
        LOGGER.info("Disconnect...");
    }


    @Override
    public synchronized void initConnection(DeviceOptions deviceOptions) {
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
            connect();
        } catch (MqttException e) {
            LOGGER.error("Can't init connection: " + e);
        }
    }

    @Override
    public synchronized boolean isTokenRefreshNeed() {
        long secsSinceRefresh = ((new DateTime()).getMillis() - tokenReceivingTime.getMillis()) / 1000;
        return secsSinceRefresh > (Cryptographer.TOKEN_EXPIRE_MINS * 60);
    }

    @Override
    public synchronized boolean isConnected() {
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
