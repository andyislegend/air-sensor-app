package net.corevalue.app.service.factory.data;

import net.corevalue.app.constant.ClientType;
import net.corevalue.app.device.Device;

public interface DataAnalyzerConstructor<T extends Device, R> {

    DataAnalyzer<T, R> constructDataAnalyzer(ClientType clientType);
}
