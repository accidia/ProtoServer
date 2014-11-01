jrz
===
[![Build Status](https://travis-ci.org/accidia/jrz.png?branch=master)](https://travis-ci.org/accidia/jrz)

## What is 'jrz', and why?



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
}
```