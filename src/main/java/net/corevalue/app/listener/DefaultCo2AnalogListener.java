package net.corevalue.app.listener;

import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;

import javax.inject.Singleton;
//TODO: implement
@Singleton
public class DefaultCo2AnalogListener implements GpioPinListenerAnalog {
    @Override
    public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
    }
}
