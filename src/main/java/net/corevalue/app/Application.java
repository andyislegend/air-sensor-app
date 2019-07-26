package net.corevalue.app;

import io.micronaut.configuration.picocli.PicocliRunner;
import net.corevalue.app.service.facade.ApplicationFacade;
import net.corevalue.app.util.CliArguments;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "air-sensor-app", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    @Inject
    private ApplicationFacade applicationFacade;

    @ArgGroup(exclusive = false)
    private CliArguments cliArguments;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }

    @Override
    public void run() {
        applicationFacade.startApplication(cliArguments);
    }
}
