package org.accidia.protoserver.services;

import com.google.common.util.concurrent.ListenableFuture;

import static org.accidia.protoserver.protos.ProtoServerProtos.Guid;

public interface IGuidService {

    Guid getGuid();

    ListenableFuture<Guid> getGuidAsync();
}
