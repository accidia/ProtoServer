package org.accidia.jrz.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.google.common.util.concurrent.Uninterruptibles;
import org.accidia.jrz.IJrzApplication;
import org.accidia.jrz.JrzApplicationTestGuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotNull;

public class GuidResourceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final IJrzApplication application;

    public GuidResourceTest() {
        this.application = new JrzApplicationTestGuid();
    }

    @BeforeClass
    public void setUp() throws Exception {
        logger.debug("setUp");
        this.application.startServer();
        while (!this.application.getServer().isStarted()) {
            logger.debug("server is not started yet; sleeping for a second...");
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        logger.debug("tearDown");
        this.application.stopServer();
    }

    @Test
    public void testGetGuid() {
        logger.debug("testGetGuid");

        final WebTarget target = ClientBuilder.newClient().target(JrzApplicationTestGuid.BASE_URI);
        final String responseMsg = target.path(".guid").request().get(String.class);
        assertNotNull(responseMsg);
    }

    @Test
    public void testGetStatus() {
        logger.debug("testGetStatus");

        final WebTarget target = ClientBuilder.newClient().target(JrzApplicationTestGuid.BASE_URI);
        final String responseMsg = target.path(".status").request().get(String.class);
        assertNotNull(responseMsg);
    }
}

