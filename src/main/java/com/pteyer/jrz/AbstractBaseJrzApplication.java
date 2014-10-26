package com.pteyer.jrz;

import com.google.common.base.Preconditions;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static com.google.common.base.Preconditions.checkState;

public abstract class AbstractBaseJrzApplication implements IJrzApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceConfig resourceConfig = getResourceConfig()
            .packages("com.pteyer.jrz");
    private Server server = JettyHttpContainerFactory.createServer(getBaseUri(),
            this.resourceConfig, false);

    @Override
    public void startServer() throws Exception {
        logger.info("starting server at {}", getBaseUri().toString());
        checkState(this.server != null, "null server");
        this.server.start();
    }

    @Override
    public void joinOnServer() throws InterruptedException {
        logger.info("joining on server");
        checkState(this.server != null, "null server");
        this.server.join();
    }

    @Override
    public void stopServer() throws Exception {
        logger.info("stopping server");
        checkState(this.server != null, "null server");
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

