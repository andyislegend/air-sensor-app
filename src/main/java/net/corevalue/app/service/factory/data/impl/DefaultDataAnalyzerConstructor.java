package net.corevalue.app.service.factory.data.impl;

import net.corevalue.app.constant.ClientType;
import net.corevalue.app.device.Device;
import net.corevalue.app.service.factory.data.DataAnalyzer;
import net.corevalue.app.service.factory.data.DataAnalyzerConstructor;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DefaultDataAnalyzerConstructor implements DataAnalyzerConstructor<Device, Object> {

    private Map<ClientType, DataAnalyzer<Device, Object>> analyzerMap;

    @PostConstruct
    public void init(@Named("MqttDeviceDataAnalyzer") DataAnalyzer<Device, Object> mqqtAnalyzer) {
        analyzerMap = new HashMap<>();
        analyzerMap.put(ClientType.MQTT, mqqtAnalyzer);
    }

    @Override
    public DataAnalyzer<Device, Object> constructDataAnalyzer(ClientType clientType) {
        return analyzerMap.get(clientType);
    }
}
