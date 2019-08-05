package net.corevalue.app.service.factory.device.impl;

import net.corevalue.app.constant.DeviceType;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.sensor.Sensor;
import net.corevalue.app.service.factory.device.DeviceAbstractFactory;
import net.corevalue.app.service.factory.device.DeviceConstructor;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DefaultDeviceConstructor implements DeviceConstructor {

    private Map<DeviceType, DeviceAbstractFactory> factoryMap;

    @PostConstruct
    public void init(@Named("RaspberryFactory") DeviceAbstractFactory raspFactory) {
        Map<DeviceType, DeviceAbstractFactory> deviceAbstractFactoryMap = new HashMap<>();
        deviceAbstractFactoryMap.put(DeviceType.RASPBERRY, raspFactory);
        factoryMap = Collections.unmodifiableMap(deviceAbstractFactoryMap);
    }

    @Override
    public Device constructDevice(DeviceType deviceType) throws Exception {
        DeviceAbstractFactory deviceFactory = factoryMap.get(deviceType);
        Device device = deviceFactory.createDevice();
        Map<SensorType, Sensor> sensorMap = initSensorMap(deviceFactory);
        device.setSensorMap(sensorMap);
        return device;
    }

    private Map<SensorType, Sensor> initSensorMap(DeviceAbstractFactory deviceFactory) throws Exception {
        Map<SensorType, Sensor> sensorMap = new HashMap<>();
        sensorMap.put(SensorType.CO2_SENSOR, deviceFactory.createCo2Sensor());
        return sensorMap;
    }
}
