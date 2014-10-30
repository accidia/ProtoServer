package org.accidia.jrz.services.impl;

import org.accidia.jrz.services.IGuidService;

import java.util.UUID;

import static org.accidia.jrz.protos.JrzProtos.Guid;

public class GuidServiceImpl implements IGuidService {
    @Override
    public Guid getGuid() {
        return Guid.newBuilder()
                .setGuid(UUID.randomUUID().toString())
                .setTimestampUtc(System.currentTimeMillis())
                .build();
    }
}
