package net.corevalue.app;

import io.micronaut.configuration.picocli.PicocliRunner;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.DeviceOptions;
import net.corevalue.app.service.Client;
import net.corevalue.app.service.DataAnalyzer;
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
    private static final int TIMEOUT = 5000;

    @Inject
    private Device device;

    @Inject
    private Client<Device> client;

    @Inject
    private DataAnalyzer<Device, MqttMessage> dataAnalyzer;

    @ArgGroup(exclusive = false)
    private DeviceOptions options;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }

    @Override
    public void run() {
        client.initConnection(options);
        client.setCallBack(device);
        while (true) {
            try {
                MqttMessage mqttMessage = dataAnalyzer.prepareDeviceData(device, SensorType.CO2_SENSOR);
                client.sendMessage(mqttMessage);
                Thread.sleep(TIMEOUT);
            } catch (Exception e) {
                LOGGER.error("can't send message: " + e);
            }
        }
    }
}
