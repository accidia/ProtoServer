package com.pteyer.jrz;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class MainTest {
    @Test
    public void testMainIllegalArgumentException() throws Exception {
        final String[] arguments = new String[]{"--some-random-argument", "--help"};
        try {
            Main.main(arguments);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "invalid argument: " + Arrays.toString(arguments));
        }
    }

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