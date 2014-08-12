package com.pteyer.jrz;

import com.google.inject.Guice;
import com.pteyer.jrz.modules.JrzModulle;
import org.eclipse.jetty.server.Server;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:3891/jrz/";
    private final String[] cliArguments;

    public Main(final String[] cliArguments) {
        this.cliArguments = cliArguments;
    }

    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.pteyer.jrz package
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.pteyer.jrz");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void mainGrizzly(String[] args) throws IOException {
        Guice.createInjector(new JrzModulle());
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

    public static void main(final String[] cliArguments) throws Exception {
        final Main theMainApplication = new Main(cliArguments);

        final ResourceConfig config = new ResourceConfig()
                .packages("com.pteyer.jrz");
        Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
        server.start();
        server.join();
    }
}

