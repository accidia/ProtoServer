package com.pteyer.jrz;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:3891/jrz/";

    public static void showUsage(final PrintStream printStream) {
        printStream.print(Constants.Main.USAGE_STRING);
    }

    public static void main(final String[] cliArguments) throws Exception {
        if (cliArguments.length >= 1) {
            if (("help".equalsIgnoreCase(cliArguments[0])
                    || ("--help".equalsIgnoreCase(cliArguments[0]))
                    || ("-h".equalsIgnoreCase(cliArguments[0])))) {
                showUsage(System.out);
                return;
            } else {
                // all parameters has to be passed as environment variables
                showUsage(System.err);
                throw new IllegalArgumentException("invalid argument: " + Arrays.toString(cliArguments));
            }
        }

        final Main theMainApplication = new Main();

        final ResourceConfig config = new ResourceConfig()
                .packages("com.pteyer.jrz");
        Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
        server.start();
        server.join();
    }
}

