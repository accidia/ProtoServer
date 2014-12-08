package org.accidia.jrz.resources;

import org.accidia.jrz.IJrzApplication;
import org.accidia.jrz.JrzTestApplication;
import org.accidia.jrz.misc.MediaTypes;
import org.accidia.jrz.protos.JrzProtos;
import org.accidia.jrz.providers.ProtobufMessageReader;
import org.accidia.jrz.providers.ProtobufMessageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.testng.Assert.*;

public class StatusResourceTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final IJrzApplication application;
    private final WebTarget webTarget;

    public StatusResourceTest() {
        this.application = new JrzTestApplication();
        this.webTarget = ClientBuilder.newClient()
                .register(ProtobufMessageReader.class)
                .register(ProtobufMessageWriter.class)
                .target(JrzTestApplication.BASE_URI);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        logger.debug("setUp");
        this.application.startServer();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        logger.debug("tearDown");
        this.application.stopServer();
    }

    @Test
    public void testGetStatus() {
        logger.debug("testGetStatus");

        final JrzProtos.Status status = this.webTarget.path(".status")
                .request(MediaTypes.APPLICATION_PROTOBUF)
                .get(JrzProtos.Status.class);
        assertNotNull(status);
        logger.info("status recieved: {}", status);
    }
}