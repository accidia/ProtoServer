package com.pteyer.jrz.services.impl;

import com.pteyer.jrz.services.IStatusService;
import com.pteyer.jrz.protos.JrzProtos;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;

import static com.pteyer.jrz.protos.JrzProtos.Status.Environment;
import static com.pteyer.jrz.protos.JrzProtos.Status.Property;

public class StatusServiceImpl implements IStatusService {
    @Override
    public JrzProtos.Status getStatus() {
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final JrzProtos.Status.Builder builder = JrzProtos.Status.newBuilder();
        builder.setCpuTime(String.valueOf(threadMXBean.getCurrentThreadCpuTime()));
        builder.setUserTime(String.valueOf(threadMXBean.getCurrentThreadUserTime()));
        builder.setSystemTime(String.valueOf(threadMXBean.getCurrentThreadCpuTime() - threadMXBean.getCurrentThreadUserTime()));
        for (String name : System.getProperties().stringPropertyNames()) {
            builder.addProperties(Property.newBuilder()
                            .setKey(name)
                            .setValue(System.getProperty(name))
            );
        }
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            builder.addEnvironments(Environment.newBuilder()
                            .setKey(entry.getKey())
                            .setValue(entry.getValue())
                            .build()
            );

        }
        return builder.build();
    }
}
