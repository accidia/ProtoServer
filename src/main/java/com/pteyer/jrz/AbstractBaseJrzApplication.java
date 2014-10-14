package com.pteyer.jrz;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public abstract class AbstractBaseJrzApplication implements IJrzApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Server server;

    @Override
    public void startServer() throws Exception {
        logger.info("starting server at {}", getBaseUri().toString());
        final ResourceConfig resourceConfig = getResourceConfig()
                .packages("com.pteyer.jrz");
        this.server = JettyHttpContainerFactory.createServer(getBaseUri(), resourceConfig);
        this.server.start();
    }

    @Override
    public void joinOnServer() throws InterruptedException {
        logger.info("joining on server");
        this.server.join();
    }

    @Override
    public void stopServer() throws Exception {
        logger.info("stopping server");
        this.server.getServer().stop();
        this.server.destroy();
    }

    @Override
    public Server getServer() {
        return this.server;
    }


    protected abstract ResourceConfig getResourceConfig();
    protected abstract URI getBaseUri();
}

