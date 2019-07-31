package net.corevalue.app.service.factory.client;

import net.corevalue.app.client.Client;
import net.corevalue.app.device.Device;
import net.corevalue.app.util.ConnectionArguments;

public abstract class ClientCreator<T extends Device> {

    public Client<T> createClient(ConnectionArguments connectionArguments, T device) throws Exception {
        Client<T> client = createConcreteClient();
        client.initConnection(connectionArguments);
        client.setCallBack(device);
        return client;
    }

    protected abstract Client<T> createConcreteClient();
}
