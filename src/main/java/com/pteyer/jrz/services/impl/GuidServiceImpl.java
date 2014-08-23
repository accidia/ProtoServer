package com.pteyer.jrz.services.impl;

import com.pteyer.jrz.services.IGuidService;

import java.util.UUID;

import static com.pteyer.jrz.protos.JrzProtos.Guid;

public class GuidServiceImpl implements IGuidService {
    @Override
    public Guid getGuid() {
        return Guid.newBuilder()
                .setGuid(UUID.randomUUID().toString())
                .setTimestampUtc(System.currentTimeMillis())
                .build();
    }
}
