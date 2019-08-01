package net.corevalue.app.device;

import lombok.Data;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.device.sensor.Sensor;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
public class Raspberry implements Device {
    private static final Logger LOGGER = LoggerFactory.getLogger(Raspberry.class);
    private Map<SensorType, Sensor> sensorMap;
    private boolean enabled = true;

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
    public SensorData readDeviceData(SensorType sensorType) {
        Sensor sensor = getSensor(sensorType);
        if (sensor == null) {
            throw new UnsupportedOperationException("Invalid sensor type!");
        }
        return sensor.readSensorData();
    }

    @Override
    public Sensor getSensor(SensorType sensorType) {
        return this.sensorMap.get(sensorType);
    }
}
