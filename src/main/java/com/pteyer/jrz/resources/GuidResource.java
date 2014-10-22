package com.pteyer.jrz.resources;

import com.pteyer.jrz.services.IGuidService;
import com.pteyer.jrz.services.impl.GuidServiceImpl;
import com.pteyer.jrz.protos.JrzProtos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    @Produces({MediaType.APPLICATION_JSON, com.pteyer.jrz.misc.MediaType.APPLICATION_PROTOBUF + ";qs=0.5"})
    public JrzProtos.Guid getGuid() {
        return this.service.getGuid();
    }
}
