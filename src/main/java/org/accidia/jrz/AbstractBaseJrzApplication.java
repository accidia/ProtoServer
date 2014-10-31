package org.accidia.jrz;

import org.accidia.jrz.misc.JrzException;
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
            .packages("com.accidia.jrz.resources");

    private Server server = JettyHttpContainerFactory.createServer(
            getBaseUri(), this.resourceConfig,
            false  // do not start
    );

    @Override
    public void startServer() throws JrzException {
        checkState(this.server != null, "null server");
        doStartServer();
    }

    @Override
    public void joinOnServer() throws JrzException {
        checkState(this.server != null, "null server");
        doJoinOnServer();
    }

    @Override
    public void stopServer() throws JrzException {
        checkState(this.server != null, "null server");
        doStopServer();
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    protected void doStartServer() throws JrzException {
        logger.info("starting jrz server at {}", getBaseUri().toString());
        try {
            this.server.start();
        } catch (Exception e) {
            logger.error("exception caught at server.start -> rethrowing", e);
            throw new JrzException(e);
        }
    }

    protected void doJoinOnServer() throws JrzException {
        logger.info("joining on server");
        try {
            this.server.join();
        } catch (Exception e) {
            logger.error("exception caught at server.join -> rethrowing", e);
            throw new JrzException(e);
        }
    }

    protected void doStopServer() throws JrzException {
        logger.info("stopping server");
        try {
            this.server.getServer().stop();
            this.server.destroy();
        } catch (Exception e) {
            logger.error("exception caught at server.stop/destroy -> rethrowing", e);
            throw new JrzException(e);
        }
    }

    protected abstract ResourceConfig getResourceConfig();
    protected abstract URI getBaseUri();
}

