package net.corevalue.app.device.factory;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.Raspberry;
import net.corevalue.app.device.sensor.Co2Sensor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Factory
public class DeviceFactory {
    @Inject
    private GpioPinListenerDigital digitalListener;
    @Inject
    private GpioPinListenerAnalog analogListener;

    @Primary
    @Bean
    @Singleton
    public Device raspDevice() {
        Co2Sensor sensor = new Co2Sensor(RaspiPin.GPIO_07, null);
        sensor.addDigitalGpioPinListener(digitalListener);
//        sensor.addAnalogGpioPinListener(analogListener);
        return new Raspberry(sensor);
    }
}
