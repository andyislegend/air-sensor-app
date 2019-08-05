package net.corevalue.app.service.factory.data;

import net.corevalue.app.constant.SensorType;
import net.corevalue.app.device.Device;

public interface DataAnalyzer<T extends Device, R> {

    R getDeviceData(T device, SensorType... sensorTypes);
}
