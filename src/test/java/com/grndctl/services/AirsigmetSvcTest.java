/**
 * MIT License
 *
 * Copyright (c) 2017 grndctl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grndctl.services;

import com.grndctl.SvcTestSupport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.String.format;

/**
 */
public class AirsigmetSvcTest extends SvcTestSupport {

    private static final String TEST_NAME = AirlineSvcTest.class.getName();

    public AirsigmetSvcTest() {
        super();
    }

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    public void getAirsigmets() throws Exception {
        airsigmetSvc.getAirsigmets().forEach(airsigmet -> LOG.info(airsigmet.getRawText()));
    }

    @Test
    public void getAirsigmentsByAlt() throws Exception {
        airsigmetSvc.getAirsigmetsInAltitudeRange(10000, 40000, 3.0)
                .forEach(airsigmet -> {
                    assert airsigmet != null;
                    LOG.info(airsigmet.getRawText());
                });
    }

    @Test
    public void getAirsigmetsByLatLon() throws Exception {
        airsigmetSvc.getAirsigmetsInLatLongRectangle(0, 0, 90, 180, 3.0)
                .forEach(airsigmet -> {
                    assert airsigmet != null;
                    LOG.info(airsigmet.getRawText());
                });
    }


}
