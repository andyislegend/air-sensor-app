package net.corevalue.app.service.factory.client.impl;

import net.corevalue.app.client.Client;
import net.corevalue.app.constant.ClientType;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.factory.client.ClientConstructor;
import net.corevalue.app.service.factory.client.ClientCreator;
import net.corevalue.app.util.ConnectionArguments;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DefaultClientConstructor implements ClientConstructor<Device, Object> {

    private Map<ClientType, ClientCreator<Device, Object>> creatorMap;

    @PostConstruct
    public void init(@Named("MqttClientCreator") ClientCreator<Device, Object> mqttClientCreator) {
        Map<ClientType, ClientCreator<Device, Object>> clientCreatorMap = new HashMap<>();
        clientCreatorMap.put(ClientType.MQTT, mqttClientCreator);
        creatorMap = Collections.unmodifiableMap(clientCreatorMap);
    }

    @Override
    public Client<Device, Object> constructClient(ClientType clientType, ConnectionArguments connectionArguments, Device device) throws Exception {
        ClientCreator<Device, Object> clientCreator = creatorMap.get(clientType);
        return clientCreator.createClient(connectionArguments, device);
    }
}
