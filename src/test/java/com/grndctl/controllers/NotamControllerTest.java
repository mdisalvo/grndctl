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
import com.grndctl.model.flightplan.Notam;
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
public class NotamControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = NotamControllerTest.class.getSimpleName();

    private static final Map<String, String> GOOD_QUERY_PARAMS;

    static {
        GOOD_QUERY_PARAMS = new HashMap<>();
        GOOD_QUERY_PARAMS.put("codes", ICAO_CODE);
    }

    private static URL NOTAM_RESOURCE;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        NOTAM_RESOURCE = addPathParams(baseUrl(), "notam");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetNotamsForCodesDefault() throws Exception {
        URL url = addQueryParams(NOTAM_RESOURCE, GOOD_QUERY_PARAMS);
        LOG.info(url.toString());

        ((List<String>)rest().getForObject(url.toExternalForm(), List.class)).forEach(n -> {
            assert(n != null);
        });
    }

    @Test
    public void testGetNotamsForCodesReport() throws Exception {
        Map<String, String> qp = new HashMap<>(GOOD_QUERY_PARAMS);
        qp.put("reportType", Notam.ReportType.REPORT.toString());

        URL url = addQueryParams(NOTAM_RESOURCE, qp);
        LOG.info(url.toString());

        ((List<String>)rest().getForObject(url.toExternalForm(), List.class)).forEach(n -> {
            assert(n != null);
        });
    }

    @Test
    public void testGetNotamsForCodesDomestic() throws Exception {
        Map<String, String> qp = new HashMap<>(GOOD_QUERY_PARAMS);
        qp.put("formatType", Notam.FormatType.DOMESTIC.toString());

        URL url = addQueryParams(NOTAM_RESOURCE, qp);
        LOG.info(url.toString());

        ((List<String>)rest().getForObject(url.toExternalForm(), List.class)).forEach(n -> {
            assert(n != null);
        });
    }

    @Test
    public void testGetNotamsForCodesReportDomestic() throws Exception {
        Map<String, String> qp = new HashMap<>(GOOD_QUERY_PARAMS);
        qp.put("reportType", Notam.ReportType.REPORT.toString());
        qp.put("formatType", Notam.FormatType.DOMESTIC.toString());

        URL url = addQueryParams(NOTAM_RESOURCE, qp);
        LOG.info(url.toString());

        ((List<String>)rest().getForObject(url.toExternalForm(), List.class)).forEach(n -> {
            assert(n != null);
        });
    }

    @Test
    public void testGetNotamsForCodes400() throws Exception {
        Map<String, String> qp = new HashMap<>(GOOD_QUERY_PARAMS);
        qp.put("reportType", "BAD");
        qp.put("formatType", "BAD");

        URL url = addQueryParams(NOTAM_RESOURCE, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    private static List<String> notams(List<Object> objects) {
        List<String> notams = new ArrayList<>();
        objects.forEach(o -> notams.add((String)o));
        return notams;
    }

}
