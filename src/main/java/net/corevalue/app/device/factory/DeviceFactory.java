package net.corevalue.app.device.factory;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.Raspberry;
import net.corevalue.app.device.sensor.Co2Sensor;
import net.corevalue.app.device.sensor.Sensor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Factory
public class DeviceFactory {
    @Inject
    private GpioPinListenerDigital digitalListener;

    @Primary
    @Singleton
    public Device raspDevice() {
        Sensor sensor = new Co2Sensor(RaspiPin.GPIO_07, null);
        sensor.addDigitalGpioPinListener(digitalListener);
        return new Raspberry(sensor);
    }
}
