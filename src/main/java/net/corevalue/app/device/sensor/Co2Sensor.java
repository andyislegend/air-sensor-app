package net.corevalue.app.device.sensor;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import lombok.Getter;

import java.util.function.Function;

@Getter
public class Co2Sensor {
    private GpioPinDigitalInput gpioPinDigitalInput;
    private GpioPinAnalogInput gpioPinAnalogInput;

    public Co2Sensor(Pin digPinNo, Pin analPinNo) {
        GpioController gpioController = GpioFactory.getInstance();
        gpioPinDigitalInput = (GpioPinDigitalInput) initInputPin(digPinNo, gpioController::provisionDigitalInputPin);
        gpioPinAnalogInput = (GpioPinAnalogInput) initInputPin(analPinNo, gpioController::provisionAnalogInputPin);
    }

    private GpioPinInput initInputPin(Pin pin, Function<Pin, GpioPinInput> func) {
        GpioPinInput gpioPinInput = null;
        if (pin != null) {
            gpioPinInput = func.apply(pin);
            gpioPinInput.setShutdownOptions(true, PinState.LOW);
        }
        return gpioPinInput;
    }

    public void addDigitalGpioPinListener(GpioPinListenerDigital listener) {
        gpioPinDigitalInput.addListener(listener);
    }

    public void addAnalogGpioPinListener(GpioPinListenerAnalog listener) {
        gpioPinAnalogInput.addListener(listener);
    }
}
