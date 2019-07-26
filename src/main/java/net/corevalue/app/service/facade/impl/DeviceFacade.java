package net.corevalue.app.service.facade.impl;

import net.corevalue.app.client.Client;
import net.corevalue.app.client.impl.MqqtDeviceClient;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.data.DataAnalyzer;
import net.corevalue.app.service.facade.ApplicationFacade;
import net.corevalue.app.service.factory.DeviceCreator;
import net.corevalue.app.util.CliArguments;
import net.corevalue.app.util.SystemUtility;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

//TODO: add file logger
//TODO: add reconnection logic
@Singleton
public class DeviceFacade implements ApplicationFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceFacade.class);

    @Inject
    private DeviceCreator deviceCreator;

    @Inject
    private DataAnalyzer<Device, MqttMessage> dataAnalyzer;

    public void startApplication(CliArguments cliArguments) {
        Client<Device> client = new MqqtDeviceClient();
        Device device = null;
        try {
            device = deviceCreator.createDevice(cliArguments.getDeviceType());
            client.initConnection(cliArguments);
            client.setCallBack(device);
        } catch (Exception e) {
            SystemUtility.emergencySystemShutdown("Can't init client or device", e);
        }
        while (device != null && device.isEnabled()) {
            MqttMessage mqttMessage = dataAnalyzer.prepareDeviceData(device, SensorType.CO2_SENSOR);
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
