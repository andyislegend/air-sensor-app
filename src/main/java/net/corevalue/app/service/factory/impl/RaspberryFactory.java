package net.corevalue.app.service.factory.impl;

import com.pi4j.io.gpio.RaspiPin;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.Raspberry;
import net.corevalue.app.device.sensor.Mq7Co2Sensor;
import net.corevalue.app.device.sensor.Sensor;
import net.corevalue.app.service.factory.DeviceAbstractFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Singleton;

@Singleton
public class RaspberryFactory implements DeviceAbstractFactory {
    @Override
    public Device createDevice() {
        return new Raspberry();
    }

    @Override
    public Sensor createCo2Sensor() {
        return new Mq7Co2Sensor(RaspiPin.GPIO_07, null);
    }

    @Override
    public Sensor createHumiditySensor() {
        throw new NotImplementedException();
    }

    @Override
    public Sensor createTemperatureSensor() {
        throw new NotImplementedException();
    }

    @Override
    public Sensor createLightSensor() {
        throw new NotImplementedException();
    }
}
