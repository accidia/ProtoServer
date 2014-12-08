package org.accidia.jrz.resources;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.google.common.base.Strings;
import org.accidia.jrz.IJrzApplication;
import org.accidia.jrz.JrzTestApplication;
import org.accidia.jrz.misc.MediaTypes;
import org.accidia.jrz.protos.JrzProtos;
import org.accidia.jrz.providers.ProtobufMessageReader;
import org.accidia.jrz.providers.ProtobufMessageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class GuidResourceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final IJrzApplication application;
    private final WebTarget webTarget;

    public GuidResourceTest() {
        this.application = new JrzTestApplication();
        this.webTarget = ClientBuilder.newClient()
                .register(ProtobufMessageReader.class)
                .register(ProtobufMessageWriter.class)
                .target(JrzTestApplication.BASE_URI);
    }

    @BeforeClass
    public void setUp() throws Exception {
        logger.debug("setUp");
        this.application.startServer();
    }

    @AfterClass
    public void tearDown() throws Exception {
        logger.debug("tearDown");
        this.application.stopServer();
    }

    @Test
    public void testGetGuid() {
        logger.debug("testGetGuid()");

        final JrzProtos.Guid guid = this.webTarget.path(".guid")
                .request(MediaTypes.APPLICATION_PROTOBUF).get(JrzProtos.Guid.class);
        assertNotNull(guid);
        assertFalse(Strings.isNullOrEmpty(guid.getGuid()));
        assertTrue(guid.getTimestampUtc() <= System.currentTimeMillis());
        logger.info("guid received: {}", guid);
    }

    @Test
    public void testGetGuidAsync() {
        logger.debug("testGetGuidAsync()");

        final JrzProtos.Guid guid = this.webTarget.path(".guid/async")
                .request(MediaTypes.APPLICATION_PROTOBUF).get(JrzProtos.Guid.class);
        assertNotNull(guid);
        assertFalse(Strings.isNullOrEmpty(guid.getGuid()));
        assertTrue(guid.getTimestampUtc() <= System.currentTimeMillis());
        logger.info("guid received: {}", guid);
    }

}

