package org.accidia.jrz;

import org.accidia.jrz.misc.JrzException;
import org.eclipse.jetty.server.Server;

public interface IJrzApplication {

    void startServer() throws JrzException;

    void joinOnServer() throws JrzException;

    void stopServer() throws JrzException;

    Server getServer() throws JrzException;
}
