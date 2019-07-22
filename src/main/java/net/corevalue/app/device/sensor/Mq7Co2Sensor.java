package net.corevalue.app.device.sensor;

import com.pi4j.io.gpio.*;
import lombok.Data;
import net.corevalue.app.constant.AirVerdict;
import net.corevalue.app.constant.PayloadField;
import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.data.SensorData;

@Data
public class Mq7Co2Sensor implements Sensor {
    private GpioPinDigitalInput co2LevelPin;
    private GpioPinDigitalInput co2QuantityPin;

    public Mq7Co2Sensor(Pin co2LevelPin, Pin co2QuantityPin) {
        GpioController gpioController = GpioFactory.getInstance();
        this.co2LevelPin = initDigitalInputPin(co2LevelPin, gpioController);
        this.co2QuantityPin = initDigitalInputPin(co2QuantityPin, gpioController);
    }

    private GpioPinDigitalInput initDigitalInputPin(Pin pin, GpioController gpioController) {
        GpioPinDigitalInput gpioPinInput = null;
        if (pin != null) {
            gpioPinInput = gpioController.provisionDigitalInputPin(pin);
            gpioPinInput.setShutdownOptions(true, PinState.LOW);
        }
        return gpioPinInput;
    }

    @Override
    public SensorData readSensorData() {
        SensorData sensorData = new SensorData();
        sensorData.setSensorType(SensorType.CO2_SENSOR);
        AirVerdict airVerdict = co2LevelPin.getState().isHigh() ? AirVerdict.CO2_NORMAL : AirVerdict.CO2_EXCEED;
        sensorData.putData(PayloadField.CO2_AIR_VERDICT, airVerdict.getValue());
        sensorData.putData(PayloadField.CO2_QUANTITY, "not implemented yet!");
        return sensorData;
    }
}
