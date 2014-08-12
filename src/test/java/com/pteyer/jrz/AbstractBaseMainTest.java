package com.pteyer.jrz;
//
//import org.testng.annotations.Test;
//
//import java.util.Arrays;
//
//import static com.pteyer.jrz.Constants.Main.*;
//import static org.testng.Assert.*;
//
public class AbstractBaseMainTest {
//    @Test
//    public void testMainIllegalArgumentException() throws Exception {
//        final String[] arguments = new String[]{"--some-random-argument", "--help"};
//        try {
//            AbstractBaseMain.main(arguments);
//        } catch (IllegalArgumentException e) {
//            assertEquals(e.getMessage(), EXCEPTION_MESSAGE_PREFIX_INVALID_ARGUMENT + Arrays.toString(arguments));
//        }
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testMainIllegalArgumentValue() throws Exception {
//        AbstractBaseMain.main(new String[]{"--some-randomg-argument"});
//        assertTrue(false);
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testMainIllegalArgumentCount() throws Exception {
//        AbstractBaseMain.main(new String[]{"--some-random-argument", "--help"});
//        assertTrue(false);
//    }
//
//    @Test
//    public void testMainForHelp() throws Exception {
//        exception should not be thrown; only help string must be printed out
//        AbstractBaseMain.main(new String[]{"--help"});
//        AbstractBaseMain.main(new String[]{"-h"});
//        assertTrue(true);
//    }
//
//    @Test
//    public void testMainWithConfigNoValueIllegalArgumentException() throws Exception {
//        final String[] arguments = new String[]{"--config"};
//        try {
//            AbstractBaseMain.main(arguments);
//        } catch (IllegalArgumentException e) {
//            assertEquals(e.getMessage(), EXCEPTION_MESSAGE_CONFIG_VALUE_MISSING);
//        }
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testMainWithConfigNoValue() throws Exception {
//        AbstractBaseMain.main(new String[]{"--config"});
//        assertTrue(false);
//    }
//
}