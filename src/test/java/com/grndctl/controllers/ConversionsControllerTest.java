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
import com.grndctl.ExceptionModel;
import com.grndctl.model.aggregates.ConversionResult;
import com.grndctl.model.aggregates.WindComponent;
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
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    @Test
    public void testGetInchesToMillibars() throws Exception {
        double inHg = 29.92;
        Map<String, String> qp = new HashMap<>();
        qp.put("pressInches", Double.toString(inHg));

        URL url = addPathParams(CONVERSION_RESOUCE, "inchesToMillibars");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ConversionResult result = rest().getForObject(url.toExternalForm(), ConversionResult.class);

        assert (Double.compare(result.getValue().doubleValue(), inchesToMillibars(inHg)) == 0);
    }

    @Test
    public void testGetInchesToMillibars400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("pressInches", "TwoNinerNinerTwo");

        URL url = addPathParams(CONVERSION_RESOUCE, "inchesToMillibars");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    @Test
    public void testGetMillibarsToInches() throws Exception {
        double mb = 1013.2;
        Map<String, String> qp = new HashMap<>();
        qp.put("pressMillibars", Double.toString(mb));

        URL url = addPathParams(CONVERSION_RESOUCE, "millibarsToInches");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ConversionResult result = rest().getForObject(url.toExternalForm(), ConversionResult.class);

        assert (Double.compare(result.getValue().doubleValue(), millibarsToInches(mb)) == 0);
    }

    @Test
    public void testGetMillibarsToInches400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("pressMillibars", "TenThirteenPointTwo");

        URL url = addPathParams(CONVERSION_RESOUCE, "millibarsToInches");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    @Test
    public void testGetWindComponent() throws Exception {
        double windDirection = 240.0;
        double heading = 180.0;
        double windSpeed = 15.0;

        Map<String, String> qp = new HashMap<>();
        qp.put("windspeed", Double.toString(windSpeed));
        qp.put("winddirection", Double.toString(windDirection));
        qp.put("heading", Double.toString(heading));

        URL url = addPathParams(CONVERSION_RESOUCE, "windcomponent");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        WindComponent wc = rest().getForObject(url.toExternalForm(), WindComponent.class);

        assert (Double.compare(wc.getHeadWind(), getHeadWind(windDirection, heading)) == 0);
        assert (Double.compare(wc.getCrossWind(), getCrosswind(windDirection, heading)) == 0);
    }

    @Test
    public void testGetWindComponent400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("windspeed", "TwoForty");
        qp.put("winddirection", "OneEighty");
        qp.put("heading", "Fifteen");

        URL url = addPathParams(CONVERSION_RESOUCE, "windcomponent");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    /*
     ********************************************************
     * Utilities
     ********************************************************
     */

    // Pressure conversion factor
    private static final double CONV_FACTOR = 33.8639;

    // Convert degrees C to F
    private static double convertFtoC(double tempF) {
        return (tempF - 32) * (Fraction.getFraction(5, 9).doubleValue());
    }

    // Convert degrees F to C
    private static double convertCtoF(double tempC) {
        return (tempC * 1.8 + 32);
    }

    // Convert wind direction and heading into headwind
    private static double getHeadWind(double windDirection, double heading) {
        return (
                Math.cos(((windDirection * Math.PI) / 180) - ((heading * Math.PI) / 180)) * 15
        );
    }

    // Convert wind direction and heading into crosswind
    private static double getCrosswind(double windDirection, double heading) {
        return (
                Math.sin(((windDirection * Math.PI) / 180) - ((heading * Math.PI) / 180)) * 15
        );
    }

    // Convert press inHg to millibars
    private static double inchesToMillibars(double inches) {
        return (CONV_FACTOR * inches);
    }

    // Convert press millibars to inHg
    private static double millibarsToInches(double millibars) {
        return (millibars / CONV_FACTOR);
    }

}
