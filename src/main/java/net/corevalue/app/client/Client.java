package net.corevalue.app.client;

import net.corevalue.app.device.Device;
import net.corevalue.app.util.ConnectionArguments;

public interface Client<T extends Device, S> {

    void initConnection(ConnectionArguments connectionArguments) throws Exception;

    void connect() throws Exception;

    void disconnect() throws Exception;

    void sendMessage(S message) throws Exception;

    void setCallBack(T device);

    boolean isTokenRefreshNeed();

    boolean isConnected();
}
