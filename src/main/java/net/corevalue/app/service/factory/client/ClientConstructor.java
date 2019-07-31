package net.corevalue.app.service.factory.client;

import net.corevalue.app.client.Client;
import net.corevalue.app.constant.ClientType;
import net.corevalue.app.device.Device;
import net.corevalue.app.util.ConnectionArguments;

public interface ClientConstructor<T extends Device> {

    Client<T> constructClient(ClientType clientType, ConnectionArguments connectionArguments, T device) throws Exception;
}
