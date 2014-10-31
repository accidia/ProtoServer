package org.accidia.jrz;

import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class JrzTestApplication extends AbstractBaseJrzApplication {
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
