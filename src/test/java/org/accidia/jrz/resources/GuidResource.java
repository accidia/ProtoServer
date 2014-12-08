package org.accidia.jrz.resources;

import org.accidia.jrz.misc.AsyncResponses;
import org.accidia.jrz.misc.MediaTypes;
import org.accidia.jrz.services.impl.GuidServiceImpl;
import org.accidia.jrz.services.IGuidService;
import org.accidia.jrz.protos.JrzProtos;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

/**
 * Root resource (exposed at "guid" path)
 */
@Path(".guid")
public class GuidResource {
    private IGuidService service;

    public GuidResource() {
        this.service = new GuidServiceImpl();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaTypes.APPLICATION_PROTOBUF + ";qs=0.5"})
    public JrzProtos.Guid getGuid() {
        return this.service.getGuid();
    }

    @GET
    @Path("/async")
    @Produces({MediaType.APPLICATION_JSON, MediaTypes.APPLICATION_PROTOBUF + ";qs=0.5"})
    @ManagedAsync
    public void getGuidAsync(@Suspended final AsyncResponse asyncResponse) {
        AsyncResponses.addTimeoutHandler(asyncResponse, 2, TimeUnit.SECONDS);
        AsyncResponses.addCompletionCallback(asyncResponse);
        AsyncResponses.addCallbackForListenableFuture(asyncResponse, this.service.getGuidAsync());
    }

}
