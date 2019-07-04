package net.corevalue.app;

import io.micronaut.configuration.picocli.PicocliRunner;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.DeviceOptions;
import net.corevalue.app.service.Client;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

import javax.inject.Inject;

//TODO: add file logger
@Command(name = "air-sensor-app", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    @Inject
    private Device device;

    @Inject
    private Client<Device> client;

    @ArgGroup(exclusive = false)
    private DeviceOptions options;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }

    @Override
    public void run() {
        client.initConnection(options);
        client.setCallBack(device);
        device.run();
    }
}
