package com.pteyer.jrz.resources;

import com.pteyer.jrz.services.IStatusService;
import com.pteyer.jrz.services.impl.StatusServiceImpl;
import com.pteyer.jrz.protos.JrzProtos;

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
    @Produces({MediaType.APPLICATION_JSON, com.pteyer.jrz.misc.MediaType.APPLICATION_PROTOBUF})
    public JrzProtos.Status getStatus() {
        return service.getStatus();
    }
}
