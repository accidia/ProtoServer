package org.accidia.jrz.services;

import static org.accidia.jrz.protos.JrzProtos.Status;

public interface IStatusService {

    Status getStatus();
}
