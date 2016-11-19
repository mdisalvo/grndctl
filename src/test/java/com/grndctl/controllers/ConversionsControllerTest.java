/**
 * MIT License
 *
 * Copyright (c) 2016 grndctl
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
import com.grndctl.ExceptionModel;
import com.grndctl.model.aggregates.ConversionResult;
import org.apache.commons.lang3.math.Fraction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 */
public class ConversionsControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = ConversionsControllerTest.class.getSimpleName();

    private static URL CONVERSION_RESOUCE;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        CONVERSION_RESOUCE = addPathParams(baseUrl(), "conversions");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetFtoC() throws Exception {
        double tempF = 50.5;
        Map<String, String> qp = new HashMap<>();
        qp.put("tempF", Double.toString(tempF));

        URL url = addPathParams(CONVERSION_RESOUCE, "FtoC");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ConversionResult result = rest().getForObject(url.toExternalForm(), ConversionResult.class);

        assert (Double.compare(result.getValue().doubleValue(), convertFtoC(tempF)) == 0);
    }

    @Test
    public void testGetFtoC400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("tempF", "Fifty");

        URL url = addPathParams(CONVERSION_RESOUCE, "FtoC");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    @Test
    public void testGetCtoF() throws Exception {
        Map<String, String> qp = new HashMap<>();
        double tempC = 22.3;
        qp.put("tempC", Double.toString(tempC));

        URL url = addPathParams(CONVERSION_RESOUCE, "CtoF");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ConversionResult result = rest().getForObject(url.toExternalForm(), ConversionResult.class);

        assert (Double.compare(result.getValue().doubleValue(), convertCtoF(tempC)) == 0);
    }

    @Test
    public void testGetCtoF400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("tempC", "TwentyTwoPointThree");

        URL url = addPathParams(CONVERSION_RESOUCE, "CtoF");
        url = addQueryParams(url, qp);

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    private static double convertFtoC(double tempF) {
        return (tempF - 32) * (Fraction.getFraction(5, 9).doubleValue());
    }
    private static double convertCtoF(double tempC) {
        return (tempC * 1.8 + 32);
    }

}
