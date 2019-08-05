package net.corevalue.app.service.factory.device;

import net.corevalue.app.device.Device;
import net.corevalue.app.device.sensor.Sensor;

public interface DeviceAbstractFactory {
    Device createDevice();

    Sensor createCo2Sensor() throws Exception;

    Sensor createHumiditySensor();

    Sensor createTemperatureSensor();

    Sensor createLightSensor();
}
