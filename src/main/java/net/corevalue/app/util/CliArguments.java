package net.corevalue.app.util;

import lombok.Getter;
import net.corevalue.app.constant.ClientType;
import net.corevalue.app.constant.DeviceType;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

@Getter
public class CliArguments {

    @ArgGroup(exclusive = false)
    private ConnectionArguments connectionArguments;

    @Option(names = "-send_timeout", required = true)
    private int sendTimeout;

    @Option(names = "-device_type", required = true)
    private DeviceType deviceType;

    @Option(names = "-client_type", required = true)
    private ClientType clientType;
}
