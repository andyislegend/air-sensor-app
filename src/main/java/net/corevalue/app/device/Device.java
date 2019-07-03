package net.corevalue.app.device;

import org.eclipse.paho.client.mqttv3.MqttCallback;

public interface Device extends MqttCallback {

    default void run() {
        while (true) {
        }
    }
}
