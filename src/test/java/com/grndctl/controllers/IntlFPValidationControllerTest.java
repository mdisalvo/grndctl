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
package com.grndctl.controllers;

import com.grndctl.ControllerTestSupport;
import com.grndctl.model.flightplan.ValidationResults;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * http://javabeat.net/spring-boot-testing/
 */
public class IntlFPValidationControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = IntlFPValidationControllerTest.class.getSimpleName();

    private static final String SAMPLE_FP =
            "(FPL-DLH560_IS" +
            "-1A319/M-SHWY/S" +
            "-EGLL0600" +
            "-N0420F370 BPK UQ295 CLN UL620 ARTOV UP44 SOMVA UP155 OKOKO UZ303 DHE UP729 DOSUR P729 LUGAS" +
            "-EKCH0715 ESMS)";

    private static URL FP_VALIDATION_RESOURCE;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        FP_VALIDATION_RESOURCE = addPathParams(baseUrl(), "intlfpvalidator");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    // TODO build out test a bit more
    @Test
    public void testPostValidateFlightPlan() throws Exception {
        Map<String, String> requestObject = new HashMap<>();
        requestObject.put("flightPlan", SAMPLE_FP);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        URL url = addPathParams(FP_VALIDATION_RESOURCE, "validate");
        LOG.info(url.toString());

        ValidationResults results = rest().postForObject(url.toURI(), requestObject, ValidationResults.class);

        assert (!results.getMessages().isEmpty());
    }

}
