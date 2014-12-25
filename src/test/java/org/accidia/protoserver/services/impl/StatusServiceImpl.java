package org.accidia.protoserver.services.impl;

import org.accidia.protoserver.services.IStatusService;
import org.accidia.protoserver.protos.ProtoServerProtos;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;

public class StatusServiceImpl implements IStatusService {
    @Override
    public ProtoServerProtos.Status getStatus() {
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ProtoServerProtos.Status.Builder builder = ProtoServerProtos.Status.newBuilder();
        builder.setCpuTime(String.valueOf(threadMXBean.getCurrentThreadCpuTime()));
        builder.setUserTime(String.valueOf(threadMXBean.getCurrentThreadUserTime()));
        builder.setSystemTime(String.valueOf(threadMXBean.getCurrentThreadCpuTime() - threadMXBean.getCurrentThreadUserTime()));
        for (String name : System.getProperties().stringPropertyNames()) {
            builder.addProperties(ProtoServerProtos.Status.Property.newBuilder()
                            .setKey(name)
                            .setValue(System.getProperty(name))
            );
        }
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            builder.addEnvironments(ProtoServerProtos.Status.Environment.newBuilder()
                            .setKey(entry.getKey())
                            .setValue(entry.getValue())
                            .build()
            );

        }
        return builder.build();
    }
}
