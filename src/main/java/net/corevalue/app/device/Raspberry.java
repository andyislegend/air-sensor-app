package net.corevalue.app.device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.corevalue.app.device.sensor.Sensor;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO: add another sensors
@Getter
@AllArgsConstructor
public class Raspberry implements Device {
    private static final Logger LOGGER = LoggerFactory.getLogger(Raspberry.class);
    private Sensor sensor;

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.error("Connection lost: " + throwable);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        LOGGER.info("Message arrived: " + s + ", " + mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.info("Delivery completed: " + iMqttDeliveryToken.getMessageId());
    }

    @Override
    public void run() {
        LOGGER.info("Run device...");
        while (true) {
        }
    }
}
