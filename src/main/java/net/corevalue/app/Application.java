package net.corevalue.app;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.annotation.Factory;
import net.corevalue.app.device.Device;
import net.corevalue.app.device.DeviceOptions;
import net.corevalue.app.service.Client;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

import javax.inject.Inject;

//TODO: implement bean fabric for client
//TODO: add file logger
//TODO: added concurrency support
@Command(name = "air-sensor-app", mixinStandardHelpOptions = true)
@Factory
public class Application implements Runnable {

    @Inject
    private Device device;

    @Inject
    private Client<Device> client;

    @Inject
    @ArgGroup(exclusive = false)
    private DeviceOptions options;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }

    @Override
    public void run() {
        client.init(options);
        client.setCallBack(device);
        device.run();
    }
}
