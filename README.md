jrz
===
[![Build Status](https://travis-ci.org/accidia/jrz.png?branch=master)](https://travis-ci.org/accidia/jrz)


## How to use?

Latest build: https://github.com/accidia/jrz/archive/master.zip

Or use it as a maven dependency:

```
<dependencies>
    <dependency>
        <groupId>org.accidia</groupId>
        <artifactId>jrz</artifactId>
        <version>0.0.13</version>
    </dependency>
</dependencies>
```

Define the data model in protocol buffers:

```
option java_package = "org.accidia.jrz.sample.protos";
option java_outer_classname = "JrzSampleProtos";

message Guid {
    required string guid = 1;
    required uint64 timestamp_utc = 2;
}

```

Define the service interfaces:

```
package org.accidia.jrz.sample.services;

import static org.accidia.jrz.sample.protos.JrzProtos.Guid;

public interface IGuidService {

    // returns an instance of Guid
    Guid getGuid();
}
```

Implement services:

```
package org.accidia.jrz.sample.services.impl;

import org.accidia.jrz.sample.services.IGuidService;

import java.util.UUID;

import static org.accidia.jrz.sample.protos.JrzProtos.Guid;

public class GuidServiceImpl implements IGuidService {

    // construct a guid object: 
    //   assign a random uuid for guid
    //   assign the current time for timestamp
    @Override
    public Guid getGuid() {
        return Guid.newBuilder()
                .setGuid(UUID.randomUUID().toString())
                .setTimestampUtc(System.currentTimeMillis())
                .build();
    }
}

```

Define and implement a resource endpoint:

```
package org.accidia.jrz.resources;

import org.accidia.jrz.services.impl.GuidServiceImpl;
import org.accidia.jrz.services.IGuidService;
import org.accidia.jrz.protos.JrzProtos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/guid")
public class GuidResource {
    private IGuidService service;

    public GuidResource() {
        this.service = new GuidServiceImpl();
    }

    @GET
    @Produces({"application/json", "application/x-protobuf;qs=0.5"})
    public JrzProtos.Guid getGuid() {
        return this.service.getGuid();
    }
}

```


Create a micro-service instance:

```
package org.accidia.jrz;

import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class SampleApplication extends AbstractBaseJrzApplication {
    public static final URI BASE_URI = URI.create("http://127.0.0.1:8080/");
    // public static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");

    @Override
    public ResourceConfig getResourceConfig() {
            return new ResourceConfig()
                .packages("org.accidia.jrz.sample");
    }

    @Override
    public URI getBaseUri() {
        return BASE_URI;
    }
    
    public static void main(final String[] args) {
        final IJrzApplication application = new SampleApplication();
        application.startServer();
        application.joinOnServer();
        
        application.stopServer();
    }
}
```
