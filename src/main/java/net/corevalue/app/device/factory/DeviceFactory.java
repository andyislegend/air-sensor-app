package net.corevalue.app.device.factory;

import com.pi4j.io.gpio.RaspiPin;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.Raspberry;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.device.sensor.Co2Sensor;
import net.corevalue.app.device.sensor.Sensor;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Factory
public class DeviceFactory {

    @Primary
    @Singleton
    public Device raspDevice() {
        Sensor co2Sensor = new Co2Sensor(RaspiPin.GPIO_07, null);
        Device device = new Raspberry(co2Sensor);
        initReadStrategyMap(device);
        return device;
    }

    private void initReadStrategyMap(Device device) {
        Map<SensorType, Supplier<SensorData>> readStrategyMap = new HashMap<>();
        readStrategyMap.put(SensorType.CO2_SENSOR, () -> {
            Sensor co2Sensor = device.getCo2Sensor();
            return co2Sensor.getSensorData();
        });
        device.setReadStrategyMap(readStrategyMap);
    }
}
