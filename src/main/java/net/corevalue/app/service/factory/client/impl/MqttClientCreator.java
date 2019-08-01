package net.corevalue.app.service.factory.client.impl;

import net.corevalue.app.client.Client;
import net.corevalue.app.client.MqqtDeviceClient;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.factory.client.ClientCreator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.inject.Singleton;

@Singleton
public class MqttClientCreator extends ClientCreator<Device, MqttMessage> {
    @Override
    public Client<Device, MqttMessage> createConcreteClient() {
        return new MqqtDeviceClient();
    }
}
