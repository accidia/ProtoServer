package org.accidia.protoserver.resources;

import org.accidia.protoserver.misc.MediaTypes;
import org.accidia.protoserver.services.IStatusService;
import org.accidia.protoserver.services.impl.StatusServiceImpl;
import org.accidia.protoserver.protos.ProtoServerProtos;

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
    @Produces({MediaType.APPLICATION_JSON, MediaTypes.APPLICATION_PROTOBUF + ";qs=0.5"})
    public ProtoServerProtos.Status getStatus() {
        return service.getStatus();
    }
}
