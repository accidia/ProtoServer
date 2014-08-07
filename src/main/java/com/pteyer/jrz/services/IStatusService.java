package com.pteyer.jrz.services;

import com.pteyer.jrz.protos.JrzProtos;

public interface IStatusService {
    JrzProtos.Status getStatus();
}
