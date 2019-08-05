package net.corevalue.app.service.facade.impl;

import net.corevalue.app.client.Client;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.facade.ApplicationFacade;
import net.corevalue.app.service.factory.client.ClientConstructor;
import net.corevalue.app.service.factory.data.DataAnalyzer;
import net.corevalue.app.service.factory.data.DataAnalyzerConstructor;
import net.corevalue.app.service.factory.device.DeviceConstructor;
import net.corevalue.app.util.CliArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultApplicationFacade implements ApplicationFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationFacade.class);

    private final DeviceConstructor deviceConstructor;

    private final ClientConstructor<Device, Object> clientConstructor;

    private final DataAnalyzerConstructor<Device, Object> dataAnalyzerConstructor;

    @Inject
    public DefaultApplicationFacade(DeviceConstructor deviceConstructor, ClientConstructor<Device, Object> clientConstructor,
                                    DataAnalyzerConstructor<Device, Object> dataAnalyzerConstructor) {
        this.deviceConstructor = deviceConstructor;
        this.clientConstructor = clientConstructor;
        this.dataAnalyzerConstructor = dataAnalyzerConstructor;
    }

    public void startApplication(CliArguments cliArguments) {
        Device device = null;
        Client<Device, Object> client = null;
        DataAnalyzer<Device, Object> dataAnalyzer = dataAnalyzerConstructor.constructDataAnalyzer(cliArguments.getClientType());
        try {
            device = deviceConstructor.constructDevice(cliArguments.getDeviceType());
            client = clientConstructor.constructClient(cliArguments.getClientType(), cliArguments.getConnectionArguments(), device);
        } catch (Exception e) {
            LOGGER.error("Can't create client or device: " + e);
            System.exit(-1);
        }
        while (device.isEnabled()) {
            Object message = dataAnalyzer.getDeviceData(device, SensorType.CO2_SENSOR);
            try {
                client.sendMessage(message);
                Thread.sleep(cliArguments.getSendTimeout());
            } catch (Exception e) {
                LOGGER.error("Can't send message: " + e);
            }
        }
        try {
            client.disconnect();
        } catch (Exception e) {
            LOGGER.error("Can't disconnect device: " + e);
        }
    }
}
