package net.corevalue.app.util;

import com.pi4j.io.i2c.I2CDevice;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@UtilityClass
public class AdcReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdcReader.class);

    public double readPpm(I2CDevice device) {
        int raw_adc = -1;
        try {
            raw_adc = device.read();
        } catch (IOException e) {
            LOGGER.error("Can't adc value: " + e);
        }
        return ((10000.0 / 4096.0) * raw_adc) + 200;
    }
}
