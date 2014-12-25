package org.accidia.protoserver;

import org.accidia.protoserver.misc.ProtoServerException;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public interface IProtoServerApplication {

    void startServer() throws ProtoServerException;

    void joinOnServer() throws ProtoServerException;

    void stopServer() throws ProtoServerException;

    Server getServer() throws ProtoServerException;

    ResourceConfig getResourceConfig();

    URI getBaseUri();
}
