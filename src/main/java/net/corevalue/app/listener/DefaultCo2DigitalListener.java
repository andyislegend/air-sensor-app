package net.corevalue.app.listener;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import net.corevalue.app.constant.AirVerdict;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.Client;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultCo2DigitalListener implements GpioPinListenerDigital {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCo2DigitalListener.class);

    @Inject
    private Client<Device> client;

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        AirVerdict verdict = event.getState() == PinState.HIGH ? AirVerdict.CO2_NORMAL : AirVerdict.CO2_EXCEED;
        JSONObject payload = new JSONObject();
        payload.put("verdict", verdict);
        MqttMessage message = new MqttMessage(payload.toString().getBytes());
        message.setQos(1);
        try {
            client.sendMessage(message);
        } catch (Exception e) {
            LOGGER.error("Can't publish message on digital event: " + e);
        }
    }
}
