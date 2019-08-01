package net.corevalue.app.device.sensor;

import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import lombok.Data;
import net.corevalue.app.constant.AirVerdict;
import net.corevalue.app.constant.PayloadField;
import net.corevalue.app.device.data.SensorData;
import net.corevalue.app.util.AdcReader;

import java.io.IOException;

@Data
public class Mq7Co2Sensor implements Sensor {
    private static final int DEVICE_ADDRESS = 72;
    private GpioPinDigitalInput co2LevelPin;
    private I2CDevice i2cDevice;

    public Mq7Co2Sensor(Pin co2LevelPin) throws IOException, I2CFactory.UnsupportedBusNumberException {
        GpioController gpioController = GpioFactory.getInstance();
        this.co2LevelPin = initDigitalInputPin(co2LevelPin, gpioController);
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        i2cDevice = bus.getDevice(DEVICE_ADDRESS);
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
        sensorData.setTitle(PayloadField.CO2_SENSOR);
        AirVerdict airVerdict = co2LevelPin.getState().isHigh() ? AirVerdict.CO2_NORMAL : AirVerdict.CO2_EXCEED;
        sensorData.putData(PayloadField.CO2_AIR_VERDICT, airVerdict.getValue());
        String co2Quantity = String.format("%.2f ppm", AdcReader.readPpm(i2cDevice));
        sensorData.putData(PayloadField.CO2_QUANTITY, co2Quantity);
        return sensorData;
    }
}
