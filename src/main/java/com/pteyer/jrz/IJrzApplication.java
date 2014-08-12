package com.pteyer.jrz;

public interface IJrzApplication {
    void init(final String[] cliArguments) throws Exception;
    void startServer() throws Exception;
    void joinOnServer() throws InterruptedException;
    void stopServer() throws Exception;
}
