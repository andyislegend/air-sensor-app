package net.corevalue.app.util;

import lombok.Getter;
import picocli.CommandLine.Option;

@Getter
public class ConnectionArguments {
    @Option(names = "-registry_id", required = true)
    private String registryId;

    @Option(names = "-gateway_id", required = true)
    private String gatewayId;

    @Option(names = "-private_key_file", required = true)
    private String privateKeyFile;

    @Option(names = "-project_id", required = true)
    private String projectId;

    @Option(names = "-cloud_region", required = true)
    private String cloudRegion;

    @Option(names = "-host_name", required = true)
    private String hostName;

    @Option(names = "-port", required = true)
    private int port;
}
