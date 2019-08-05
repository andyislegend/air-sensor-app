package net.corevalue.app.service.factory.device.impl;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CFactory;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.Raspberry;
import net.corevalue.app.device.sensor.Mq7Co2Sensor;
import net.corevalue.app.device.sensor.Sensor;
import net.corevalue.app.service.factory.device.DeviceAbstractFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class RaspberryFactory implements DeviceAbstractFactory {
    @Override
    public Device createDevice() {
        return new Raspberry();
    }

    @Override
    public Sensor createCo2Sensor() throws IOException, I2CFactory.UnsupportedBusNumberException {
        return new Mq7Co2Sensor(RaspiPin.GPIO_07);
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
