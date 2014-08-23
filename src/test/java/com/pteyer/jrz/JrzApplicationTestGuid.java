package com.pteyer.jrz;

import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class JrzApplicationTestGuid extends AbstractBaseJrzApplication {
    public static final String BASE_URI = "http://0.0.0.0:3891/";

    @Override
    protected ResourceConfig getResourceConfig() {
        return new ResourceConfig().packages("com.pteyer.jrz");
    }

    @Override
    protected URI getBaseUri() {
        return URI.create(BASE_URI);
    }
}
