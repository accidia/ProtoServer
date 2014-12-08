package org.accidia.jrz.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.accidia.jrz.services.IGuidService;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import static org.accidia.jrz.protos.JrzProtos.Guid;

public class GuidServiceImpl implements IGuidService {
    private final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
    @Override
    public Guid getGuid() {
        return Guid.newBuilder()
                .setGuid(UUID.randomUUID().toString())
                .setTimestampUtc(System.currentTimeMillis())
                .build();
    }

    @Override
    public ListenableFuture<Guid> getGuidAsync() {
        return this.executorService.submit(
                new Callable<Guid>() {
                    @Override
                    public Guid call() throws Exception {
                        return getGuid();
                    }
                }
        );
    }
}
