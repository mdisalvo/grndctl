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
import com.grndctl.model.airsigmet.AIRSIGMET;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 */
public class AirsigmetControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = AirsigmetControllerTest.class.getSimpleName();

    private static URL AIRSIGMET_RESOURCE;

    private static Map<String, String> GOOD_QUERY_PARAMS_ALT;

    private static Map<String, String> GOOD_QUERY_PARAMS_LAT_LON;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        AIRSIGMET_RESOURCE = addPathParams(baseUrl(), "airsigmet");

        GOOD_QUERY_PARAMS_ALT = new HashMap<>();
        GOOD_QUERY_PARAMS_ALT.put("hrsBefore", "3.0");
        GOOD_QUERY_PARAMS_ALT.put("minAltitudeFt", "10000");
        GOOD_QUERY_PARAMS_ALT.put("maxAltitudeFt", "40000");

        GOOD_QUERY_PARAMS_LAT_LON = new HashMap<>();
        GOOD_QUERY_PARAMS_LAT_LON.put("hrsBefore", "3.0");
        GOOD_QUERY_PARAMS_LAT_LON.put("minLat", "0");
        GOOD_QUERY_PARAMS_LAT_LON.put("maxLat", "90");
        GOOD_QUERY_PARAMS_LAT_LON.put("minLon", "0");
        GOOD_QUERY_PARAMS_LAT_LON.put("maxLon", "180");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetAirsigmets() throws Exception {
        assert (!airsigmets(getForListObject(AIRSIGMET_RESOURCE, AIRSIGMET.class)).isEmpty());
    }

    @Test
    public void testGetAirsigmetByAlt() throws Exception {
        URL url = addPathParams(AIRSIGMET_RESOURCE, "altLimited");
        url = addQueryParams(url, GOOD_QUERY_PARAMS_ALT);
        LOG.info(url.toString());

        assert (!airsigmets(getForListObject(url, AIRSIGMET.class)).isEmpty());
    }

    @Test
    public void testGetAirsigmetByAlt400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("hrsBefore", "exception");
        qp.put("minAltitudeFt", "tenthousand");
        qp.put("maxAltitudeFt", "fortythousand");

        URL url = addPathParams(AIRSIGMET_RESOURCE, "altLimited");
        url = addQueryParams(url, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    @Test
    public void testGetAirsigmetsByLatLon() throws Exception {
        URL url = addPathParams(AIRSIGMET_RESOURCE, "latLonLimited");
        url = addQueryParams(url, GOOD_QUERY_PARAMS_LAT_LON);
        LOG.info(url.toString());

        assert (!airsigmets(getForListObject(url, AIRSIGMET.class)).isEmpty());
    }

    @Test
    public void testGetAirsigmetsByLatLon400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("hrsBefore", "exception");
        qp.put("minLat", "zero");
        qp.put("maxLat", "ninety");
        qp.put("minLon", "zero");
        qp.put("maxLon", "onehundredandeighty");

        URL url = addPathParams(AIRSIGMET_RESOURCE, "latLonLimited");
        url = addQueryParams(url, qp);

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    private static List<AIRSIGMET> airsigmets(List<Object> objects) {
        List<AIRSIGMET> airsigmets = new ArrayList<>(objects.size());
        objects.forEach(o -> airsigmets.add((AIRSIGMET)o));
        return airsigmets;
    }

}
