package net.corevalue.app;

import io.micronaut.configuration.picocli.PicocliRunner;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.client.Client;
import net.corevalue.app.service.data.DataAnalyzer;
import net.corevalue.app.service.factory.DeviceCreator;
import net.corevalue.app.util.CliArguments;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

import javax.inject.Inject;

//TODO: add file logger
//TODO: add reconnection logic
@Command(name = "air-sensor-app", mixinStandardHelpOptions = true)
public class Application implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Inject
    private DeviceCreator deviceCreator;

    @Inject
    private Client<Device> client;

    @Inject
    private DataAnalyzer<Device, MqttMessage> dataAnalyzer;

    @ArgGroup(exclusive = false)
    private CliArguments cliArguments;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }

    @Override
    public void run() {
        Device device = deviceCreator.createDevice(cliArguments.getDeviceType());
        client.initConnection(cliArguments);
        client.setCallBack(device);
        while (device.isEnabled()) {
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
