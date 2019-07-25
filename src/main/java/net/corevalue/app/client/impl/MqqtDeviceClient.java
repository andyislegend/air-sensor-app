package net.corevalue.app.client.impl;

import lombok.Getter;
import net.corevalue.app.client.Client;
import net.corevalue.app.device.Device;
import net.corevalue.app.util.CliArguments;
import net.corevalue.app.util.Cryptographer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MqqtDeviceClient implements Client<Device> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqqtDeviceClient.class);
    private MqttClient client;
    private String mqttTelemetryTopic;
    private MqttConnectOptions connectionOptions;
    private CliArguments cliArguments;
    private DateTime tokenReceivingTime;
    @Getter
    private boolean connected;


    @Override
    public void sendMessage(MqttMessage message) throws Exception {
        if (isTokenRefreshNeed()) {
            if (isConnected()) {
                disconnect();
            }
            setToken(connectionOptions, cliArguments);
            LOGGER.info("Refreshed token...");
            connect();
        }
        client.publish(mqttTelemetryTopic, message);
    }

    @Override
    public void setCallBack(Device device) {
        client.setCallback(device);
    }

    @Override
    public void connect() throws MqttException {
        client.connect(connectionOptions);
        connected = true;
        LOGGER.info("Connection initiated...");
    }

    @Override
    public void disconnect() throws MqttException {
        client.disconnect();
        connected = false;
        LOGGER.info("Disconnect...");
    }


    @Override
    public void initConnection(CliArguments cliArguments) throws Exception {
        this.cliArguments = cliArguments;
        mqttTelemetryTopic = String.format("/devices/%s/events", cliArguments.getGatewayId());
        String mqttServerAddress = String.format("ssl://%s:%s", cliArguments.getMqttBridgeHostname(),
                cliArguments.getMqttBridgePort());
        String mqttClientId = String.format("projects/%s/locations/%s/registries/%s/devices/%s",
                cliArguments.getProjectId(), cliArguments.getCloudRegion(), cliArguments.getRegistryId(),
                cliArguments.getGatewayId());
        connectionOptions = getConnectionProperties(cliArguments);
        client = new MqttClient(mqttServerAddress, mqttClientId, new MemoryPersistence());
        connect();
    }

    @Override
    public boolean isTokenRefreshNeed() {
        long secsSinceRefresh = ((new DateTime()).getMillis() - tokenReceivingTime.getMillis()) / 1000;
        return secsSinceRefresh > (Cryptographer.TOKEN_EXPIRE_MINS * 60);
    }

    private MqttConnectOptions getConnectionProperties(CliArguments cliArguments) throws Exception {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        Properties sslProps = new Properties();
        sslProps.setProperty("com.ibm.ssl.protocol", "TLSv1.2");
        connectOptions.setSSLProperties(sslProps);
        connectOptions.setUserName("unused");
        setToken(connectOptions, cliArguments);
        return connectOptions;
    }

    private void setToken(MqttConnectOptions connectOptions, CliArguments cliArguments) throws Exception {
        connectOptions.setPassword(Cryptographer.createRSAToken(cliArguments.getProjectId(),
                cliArguments.getPrivateKeyFile()).toCharArray());
        tokenReceivingTime = new DateTime();
    }
}
