package net.corevalue.app.service.factory.device;

import net.corevalue.app.constant.DeviceType;
import net.corevalue.app.device.Device;

public interface DeviceConstructor {

    Device constructDevice(DeviceType deviceType) throws Exception;
}
