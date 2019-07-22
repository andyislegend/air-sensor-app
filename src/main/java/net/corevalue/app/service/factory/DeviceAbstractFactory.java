package net.corevalue.app.service.factory;

import net.corevalue.app.device.Device;
import net.corevalue.app.device.sensor.Sensor;

public interface DeviceAbstractFactory {
    Device createDevice();

    Sensor createCo2Sensor();

    Sensor createHumiditySensor();

    Sensor createTemperatureSensor();

    Sensor createLightSensor();
}
