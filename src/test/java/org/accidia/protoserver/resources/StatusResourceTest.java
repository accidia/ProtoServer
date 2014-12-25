package org.accidia.protoserver.resources;

import org.accidia.protoserver.IProtoServerApplication;
import org.accidia.protoserver.ProtoServerTestApplication;
import org.accidia.protoserver.misc.MediaTypes;
import org.accidia.protoserver.protos.ProtoServerProtos;
import org.accidia.protoserver.providers.ProtobufMessageReader;
import org.accidia.protoserver.providers.ProtobufMessageWriter;
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
    private final IProtoServerApplication application;
    private final WebTarget webTarget;

    public StatusResourceTest() {
        this.application = new ProtoServerTestApplication();
        this.webTarget = ClientBuilder.newClient()
                .register(ProtobufMessageReader.class)
                .register(ProtobufMessageWriter.class)
                .target(ProtoServerTestApplication.BASE_URI);
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

        final ProtoServerProtos.Status status = this.webTarget.path(".status")
                .request(MediaTypes.APPLICATION_PROTOBUF)
                .get(ProtoServerProtos.Status.class);
        assertNotNull(status);
        logger.info("status recieved: {}", status);
    }
}