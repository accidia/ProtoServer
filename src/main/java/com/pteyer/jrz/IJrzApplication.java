package com.pteyer.jrz;

import org.eclipse.jetty.server.Server;

public interface IJrzApplication {

    void startServer() throws Exception;

    void joinOnServer() throws InterruptedException;

    void stopServer() throws Exception;

    Server getServer();
}
