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
import com.grndctl.model.pirep.PIREP;
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
public class PirepControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = PirepControllerTest.class.getSimpleName();

    private static URL PIREP_RESOURCE;

    private static Map<String, String> GOOD_QUERY_PARAMS;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        PIREP_RESOURCE = addPathParams(baseUrl(), "pirep");
        GOOD_QUERY_PARAMS = new HashMap<>();
        GOOD_QUERY_PARAMS.put("hrsBefore", "5.0");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetPireps() throws Exception {
        LOG.info(PIREP_RESOURCE.toString());
        pireps(getForListObject(PIREP_RESOURCE, PIREP.class)).forEach(p -> {
            assert(p != null);
        });
    }

    @Test
    public void testGetPirepsNonDefault() throws Exception {
        URL url = addQueryParams(PIREP_RESOURCE, GOOD_QUERY_PARAMS);
        LOG.info(url.toString());

        pireps(getForListObject(url, PIREP.class)).forEach(p -> {
            assert(p != null);
        });
    }

    @Test
    public void testGetPireps400() throws Exception {
        Map<String, String> qp = new HashMap<>();
        qp.put("hrsBefore", "one");

        URL url = addQueryParams(PIREP_RESOURCE, qp);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 400;
    }

    private static List<PIREP> pireps(List<Object> objects) {
        List<PIREP> pireps = new ArrayList<>(objects.size());
        objects.forEach(o -> pireps.add((PIREP)o));
        return pireps;
    }

}
