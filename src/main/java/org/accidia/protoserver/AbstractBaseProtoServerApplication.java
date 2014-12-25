package org.accidia.protoserver;

import org.accidia.protoserver.misc.ProtoServerException;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkState;

public abstract class AbstractBaseProtoServerApplication implements IProtoServerApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceConfig resourceConfig = getResourceConfig()
            .packages("org.accidia.protoserver");

    private Server server = JettyHttpContainerFactory.createServer(
            getBaseUri(), this.resourceConfig,
            false  // do not start
    );

    @Override
    public void startServer() throws ProtoServerException {
        checkState(this.server != null, "null server");
        doStartServer();
    }

    @Override
    public void joinOnServer() throws ProtoServerException {
        checkState(this.server != null, "null server");
        doJoinOnServer();
    }

    @Override
    public void stopServer() throws ProtoServerException {
        checkState(this.server != null, "null server");
        doStopServer();
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    protected void doStartServer() throws ProtoServerException {
        logger.info("starting protoserver server at {}", getBaseUri().toString());
        try {
            this.server.start();
        } catch (Exception e) {
            logger.error("exception caught at server.start -> rethrowing", e);
            throw new ProtoServerException(e);
        }
    }

    protected void doJoinOnServer() throws ProtoServerException {
        logger.info("joining on server");
        try {
            this.server.join();
        } catch (Exception e) {
            logger.error("exception caught at server.join -> rethrowing", e);
            throw new ProtoServerException(e);
        }
    }

    protected void doStopServer() throws ProtoServerException {
        logger.info("stopping server");
        try {
            this.server.getServer().stop();
            this.server.destroy();
        } catch (Exception e) {
            logger.error("exception caught at server.stop/destroy -> rethrowing", e);
            throw new ProtoServerException(e);
        }
    }
}

