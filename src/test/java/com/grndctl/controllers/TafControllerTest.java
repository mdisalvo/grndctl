package com.grndctl.controllers;

import com.grndctl.ControllerTestSupport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.String.format;

/**
 */
public class TafControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = TafControllerTest.class.getSimpleName();

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetTaf() throws Exception {
        LOG.info("Testing....");
    }

}
