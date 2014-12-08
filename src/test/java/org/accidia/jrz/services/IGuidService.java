package org.accidia.jrz.services;

import com.google.common.util.concurrent.ListenableFuture;

import static org.accidia.jrz.protos.JrzProtos.Guid;

public interface IGuidService {

    Guid getGuid();

    ListenableFuture<Guid> getGuidAsync();
}
