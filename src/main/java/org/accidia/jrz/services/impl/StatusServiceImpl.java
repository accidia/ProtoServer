package org.accidia.jrz.services.impl;

import org.accidia.jrz.services.IStatusService;
import org.accidia.jrz.protos.JrzProtos;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;

public class StatusServiceImpl implements IStatusService {
    @Override
    public JrzProtos.Status getStatus() {
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final JrzProtos.Status.Builder builder = JrzProtos.Status.newBuilder();
        builder.setCpuTime(String.valueOf(threadMXBean.getCurrentThreadCpuTime()));
        builder.setUserTime(String.valueOf(threadMXBean.getCurrentThreadUserTime()));
        builder.setSystemTime(String.valueOf(threadMXBean.getCurrentThreadCpuTime() - threadMXBean.getCurrentThreadUserTime()));
        for (String name : System.getProperties().stringPropertyNames()) {
            builder.addProperties(JrzProtos.Status.Property.newBuilder()
                            .setKey(name)
                            .setValue(System.getProperty(name))
            );
        }
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            builder.addEnvironments(JrzProtos.Status.Environment.newBuilder()
                            .setKey(entry.getKey())
                            .setValue(entry.getValue())
                            .build()
            );

        }
        return builder.build();
    }
}
