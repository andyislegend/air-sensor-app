package net.corevalue.app.service.facade.impl;

import net.corevalue.app.client.Client;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.data.DataAnalyzer;
import net.corevalue.app.service.facade.ApplicationFacade;
import net.corevalue.app.service.factory.client.ClientConstructor;
import net.corevalue.app.service.factory.device.DeviceConstructor;
import net.corevalue.app.util.CliArguments;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

//TODO: add file logger
//TODO: add reconnection logic
@Singleton
public class DefaultApplicationFacade implements ApplicationFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationFacade.class);

    @Inject
    private DeviceConstructor deviceConstructor;

    @Inject
    private ClientConstructor<Device> clientConstructor;

    @Inject
    private DataAnalyzer<Device, MqttMessage> dataAnalyzer;

    public void startApplication(CliArguments cliArguments) {
        Device device = null;
        Client<Device> client = null;
        try {
            device = deviceConstructor.constructDevice(cliArguments.getDeviceType());
            client = clientConstructor.constructClient(cliArguments.getClientType(), cliArguments.getConnectionArguments(), device);
        } catch (Exception e) {
            LOGGER.error("Can't create client or device: " + e);
            System.exit(-1);
        }
        while (device.isEnabled()) {
            MqttMessage mqttMessage = dataAnalyzer.getDeviceData(device, SensorType.CO2_SENSOR);
            try {
                client.sendMessage(mqttMessage);
                Thread.sleep(cliArguments.getSendTimeout());
            } catch (Exception e) {
                LOGGER.error("Can't send message: " + e);
            }
        }
        try {
            client.disconnect();
        } catch (MqttException e) {
            LOGGER.error("Can't disconnect device: " + e);
        }
    }
}
