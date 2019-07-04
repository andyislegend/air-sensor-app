package net.corevalue.app.device;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Option;

@Getter
@Setter
public class DeviceOptions {

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

    @Option(names = "-mqtt_bridge_hostname", required = true)
    private String mqttBridgeHostname;

    @Option(names = "-mqtt_bridge_port", required = true)
    private int mqttBridgePort;
}
