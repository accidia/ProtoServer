package com.pteyer.jrz;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;

import static com.pteyer.jrz.Constants.Main.*;

public abstract class AbstractBaseJrzApplication implements IJrzApplication {
    private final Logger logger = LoggerFactory.getLogger(AbstractBaseJrzApplication.class);
    private Server server;
    private File configurationFile = null;

    protected void showUsage(final PrintStream printStream) {
        printStream.print(USAGE_STRING);
    }

    @Override
    public void init(final String[] cliArguments) {
        if (cliArguments != null && cliArguments.length >= 1) {
            final String firstArgument = cliArguments[0];
            if (("help".equalsIgnoreCase(firstArgument)
                    || ("--help".equalsIgnoreCase(firstArgument))
                    || ("-h".equalsIgnoreCase(firstArgument)))) {
                logger.debug("the --help argument is passed " +
                        "-> showing usage and then exit");
                showUsage(System.out);
                return;
            } else if (("config".equalsIgnoreCase(firstArgument))
                    || ("--config".equalsIgnoreCase(firstArgument))
                    || ("-c".equalsIgnoreCase(firstArgument))) {
                if (cliArguments.length < 2) {
                    logger.error("configuration file is not passed " +
                            "-> showing help and then exit");
                    showUsage(System.err);
                    throw new IllegalArgumentException(EXCEPTION_MESSAGE_CONFIG_VALUE_MISSING);
                }
                final String secondArgument = cliArguments[1];
                final File configFile = new File(secondArgument);
                if (!configFile.isFile() || !configFile.canRead()) {
                    logger.error("configuration file is not readable " +
                            "-> showing help and then exit");
                    showUsage(System.err);
                    throw new IllegalArgumentException(EXCEPTION_MESSAGE_CONFIG_INVALID_FILE);
                }
                this.configurationFile = configFile;
            } else {
                // all parameters has to be passed as environment variables
                showUsage(System.err);
                throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX_INVALID_ARGUMENT
                        + Arrays.toString(cliArguments));
            }
        }

        loadConfigurations();
        try {
            startServer();
        } catch (Exception e) {
            logger.error("caught an exception when trying to start jetty server " +
                    "-> rethrowing as runtime exception", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startServer() throws Exception {
        logger.info("starting server at {}", getBaseUri().toString());
        this.server = JettyHttpContainerFactory.createServer(getBaseUri(), getResourceConfig());
        this.server.start();
    }

    public void joinOnServer() throws InterruptedException {
        this.server.join();
    }

    public void stopServer() throws Exception {
        this.server.getServer().stop();
        this.server.destroy();
    }

    protected void loadConfigurations() {
        if (this.configurationFile == null) {
            logger.info("configuration file is not set" +
                    "-> skipping");
            return;
        }
        logger.info("loading configurations from {}", this.configurationFile.getAbsoluteFile());
        // TODO add logic to read configurations
    }

    protected abstract ResourceConfig getResourceConfig();
    protected abstract URI getBaseUri();
}

