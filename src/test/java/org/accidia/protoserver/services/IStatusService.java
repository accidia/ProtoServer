package org.accidia.protoserver.services;

import static org.accidia.protoserver.protos.ProtoServerProtos.Status;

public interface IStatusService {

    Status getStatus();
}
