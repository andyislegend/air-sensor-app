package net.corevalue.app.device.sensor;

import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public interface Sensor {
    void addDigitalGpioPinListener(GpioPinListenerDigital listener);

    void addAnalogGpioPinListener(GpioPinListenerAnalog listener);
}
