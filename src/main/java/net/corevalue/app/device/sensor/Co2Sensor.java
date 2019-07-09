package net.corevalue.app.device.sensor;

import com.pi4j.io.gpio.*;
import lombok.Getter;
import net.corevalue.app.constant.AirVerdict;
import net.corevalue.app.constant.PayloadField;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.data.SensorData;

import java.util.function.Function;

@Getter
public class Co2Sensor implements Sensor {
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


    @Override
    public SensorData getSensorData() {
        SensorData sensorData = new SensorData();
        sensorData.setSensorType(SensorType.CO2_SENSOR);
        AirVerdict airVerdict = gpioPinDigitalInput.getState().isHigh() ? AirVerdict.CO2_NORMAL : AirVerdict.CO2_EXCEED;
        sensorData.putData(PayloadField.CO2_AIR_VERDICT, airVerdict.getValue());
        sensorData.putData(PayloadField.CO2_QUANTITY, "not implemented yet!");
        return sensorData;
    }
}
