package com.pteyer.jrz;

import com.google.common.io.Files;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;

import static com.pteyer.jrz.Constants.Main.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static final String BASE_URI = "http://0.0.0.0:3891/jrz/";

    public static void showUsage(final PrintStream printStream) {
        printStream.print(USAGE_STRING);
    }

    public static void main(final String[] cliArguments) {
        if (cliArguments.length >= 1) {
            final String firstArgument = cliArguments[0];
            if (("help".equalsIgnoreCase(firstArgument)
                    || ("--help".equalsIgnoreCase(firstArgument))
                    || ("-h".equalsIgnoreCase(firstArgument)))) {
                logger.debug("the --help argument is passed -> showing usage and then exit");
                showUsage(System.out);
                return;
            } else if (("config".equalsIgnoreCase(firstArgument))
                    || ("--config".equalsIgnoreCase(firstArgument))
                    || ("-c".equalsIgnoreCase(firstArgument))) {
                if (cliArguments.length < 2) {
                    logger.error("configuration file is not passed -> showing help and then exit");
                    showUsage(System.err);
                    throw new IllegalArgumentException(EXCEPTION_MESSAGE_CONFIG_VALUE_MISSING);
                }
                final String secondArgument = cliArguments[1];
                final File configFile = new File(secondArgument);
                if (!configFile.isFile() || !configFile.canRead()) {
                    logger.error("configuration file is not readable -> showing help and then exit");
                    showUsage(System.err);
                    throw new IllegalArgumentException(EXCEPTION_MESSAGE_CONFIG_INVALID_FILE);
                }

            } else {
                // all parameters has to be passed as environment variables
                showUsage(System.err);
                throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX_INVALID_ARGUMENT + Arrays.toString(cliArguments));
            }
        }

        final Main theMainApplication = new Main();
        theMainApplication.loadConfigurations();

        final ResourceConfig config = new ResourceConfig()
                .packages("com.pteyer.jrz");
        Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
//        server.start();
//        server.join();
    }

    private void loadConfigurations() {
        logger.info("loading configurations from {}");

    }
}

