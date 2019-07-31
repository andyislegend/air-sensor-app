package net.corevalue.app.service.factory.client.impl;

import net.corevalue.app.client.Client;
import net.corevalue.app.client.impl.MqqtDeviceClient;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.factory.client.ClientCreator;

import javax.inject.Singleton;

@Singleton
public class MqttClientCreator extends ClientCreator<Device> {
    @Override
    public Client<Device> createConcreteClient() {
        return new MqqtDeviceClient();
    }
}
