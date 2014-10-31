package org.accidia.jrz;

import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class JrzApplicationTestGuid extends AbstractBaseJrzApplication {
    public static final String BASE_URI = "http://0.0.0.0:3891/";

    @Override
    public ResourceConfig getResourceConfig() {
        return new ResourceConfig();
    }

    @Override
    public URI getBaseUri() {
        return URI.create(BASE_URI);
    }
}
