package org.accidia.jrz.resources;

import org.accidia.jrz.services.IStatusService;
import org.accidia.jrz.services.impl.StatusServiceImpl;
import org.accidia.jrz.protos.JrzProtos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(".status")
public class StatusResource {
    private final IStatusService service;

    public StatusResource() {
        this.service = new StatusServiceImpl();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, org.accidia.jrz.misc.MediaType.APPLICATION_PROTOBUF + ";qs=0.5"})
    public JrzProtos.Status getStatus() {
        return service.getStatus();
    }
}
