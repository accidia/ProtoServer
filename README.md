ProtoServer - Jetty, Jersey, and Protocol Buffers
===
[![Build Status](https://travis-ci.org/accidia/protostore.png?branch=master)](https://travis-ci.org/accidia/protostore)


## How to use?

Latest build: https://github.com/accidia/jrz/archive/master.zip

Or use it as a maven dependency:

```xml
<dependency>
    <groupId>org.accidia</groupId>
    <artifactId>protoserver</artifactId>
    <version>0.0.18</version>
</dependency>
```

Define the data model in protocol buffers:

```protobuf
option java_package = "org.accidia.protoserver.sample.protos";
option java_outer_classname = "ProtoServerSampleProtos";

message Guid {
    required string guid = 1;
    required uint64 timestamp_utc = 2;
}
```

Define the service interfaces:

```java
package org.accidia.protoserver.sample.services;

import static org.accidia.protoserver.sample.protos.ProtoServerProtos.Guid;

public interface IGuidService {

    // returns an instance of Guid
    Guid getGuid();
}
```

Implement services:

```java
package org.accidia.protoserver.sample.services.impl;

import org.accidia.protoserver.sample.services.IGuidService;

import java.util.UUID;

import static org.accidia.protoserver.sample.protos.ProtoServerProtos.Guid;

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

```java
package org.accidia.protoserver.resources;

import GuidServiceImpl;
import IGuidService;
import org.accidia.protoserver.protos.ProtoServerProtos;

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
    public ProtoServerProtos.Guid getGuid() {
        return this.service.getGuid();
    }
}
```


Create a micro-service instance:

```java
package org.accidia.protoserver;

import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class SampleApplication extends AbstractBaseProtoStoreApplication {
    public static final URI BASE_URI = URI.create("http://127.0.0.1:8080/");
    // public static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");

    @Override
    public ResourceConfig getResourceConfig() {
            return new ResourceConfig()
                .packages("org.accidia.protostore.sample");
    }

    @Override
    public URI getBaseUri() {
        return BASE_URI;
    }
    
    public static void main(final String[] args) {
        final IProtoStoreApplication application = new SampleApplication();
        application.startServer();
        application.joinOnServer();
        
        application.stopServer();
    }
}
```
