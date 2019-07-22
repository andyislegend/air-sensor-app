package net.corevalue.app.service.factory.impl;

import net.corevalue.app.constant.DeviceType;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.device.sensor.Sensor;
import net.corevalue.app.service.factory.DeviceAbstractFactory;
import net.corevalue.app.service.factory.DeviceCreator;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Singleton
public class DefaultDeviceCreator implements DeviceCreator {

    private Map<DeviceType, DeviceAbstractFactory> factoryMap;

    @PostConstruct
    public void init(@Named("RaspberryFactory") DeviceAbstractFactory raspFactory) {
        factoryMap = new HashMap<>();
        factoryMap.put(DeviceType.RASPBERRY, raspFactory);
    }

    @Override
    public Device createDevice(DeviceType deviceType) {
        DeviceAbstractFactory deviceFactory = factoryMap.get(deviceType);
        Device device = deviceFactory.createDevice();
        device.setCo2Sensor(deviceFactory.createCo2Sensor());
        initReadStrategyMap(device);
        return device;
    }

    private void initReadStrategyMap(Device device) {
        Map<SensorType, Supplier<SensorData>> readStrategyMap = new HashMap<>();
        readStrategyMap.put(SensorType.CO2_SENSOR, () -> {
            Sensor co2Sensor = device.getCo2Sensor();
            return co2Sensor.readSensorData();
        });
        device.setReadStrategyMap(readStrategyMap);
    }
}
