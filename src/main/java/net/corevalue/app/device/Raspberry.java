package net.corevalue.app.device;

import lombok.Getter;
import lombok.Setter;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.device.sensor.Sensor;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

//TODO: add another sensors
@Getter
@Setter
public class Raspberry implements Device {
    private static final Logger LOGGER = LoggerFactory.getLogger(Raspberry.class);
    private Sensor co2Sensor;
    private Map<SensorType, Supplier<SensorData>> readStrategyMap;

    public Raspberry(Sensor co2Sensor) {
        this.co2Sensor = co2Sensor;
    }

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
        Supplier<SensorData> supplier = this.readStrategyMap.get(sensorType);
        if (supplier == null) {
            throw new UnsupportedOperationException("Invalid sensor type!");
        }
        return supplier.get();
    }

    @Override
    public Sensor getHumiditySensor() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public Sensor getTemperatureSensor() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public Sensor getLightSensor() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
