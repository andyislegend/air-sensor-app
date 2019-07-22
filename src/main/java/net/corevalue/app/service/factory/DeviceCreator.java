package net.corevalue.app.service.factory;

import net.corevalue.app.constant.DeviceType;
import net.corevalue.app.device.Device;

public interface DeviceCreator {

    Device createDevice(DeviceType deviceType);
}
