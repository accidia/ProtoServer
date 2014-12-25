package org.accidia.protoserver;

import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class ProtoServerTestApplication extends AbstractBaseProtoServerApplication {
    public static final URI BASE_URI = URI.create("http://localhost:3891/");

    @Override
    public ResourceConfig getResourceConfig() {
        return new ResourceConfig();
    }

    @Override
    public URI getBaseUri() {
        return BASE_URI;
    }
}
