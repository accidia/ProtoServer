package com.pteyer.jrz;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MainTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMainIllegalArgumentValue() throws Exception {
        Main.main(new String[]{"--some-randomg-argument"});
        assertTrue(false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMainIllegalArgumentCount() throws Exception {
        Main.main(new String[]{"--some-random-argument", "--help"});
        assertTrue(false);
    }

    @Test
    public void testMainForHelp() throws Exception {
        // exception should not be thrown; only help string must be printed out
        Main.main(new String[]{"--help"});
        Main.main(new String[]{"-h"});
        assertTrue(true);
    }
}