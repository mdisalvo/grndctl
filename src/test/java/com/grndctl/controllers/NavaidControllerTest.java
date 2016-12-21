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
import com.grndctl.model.flightplan.Navaid;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 */
public class NavaidControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = NavaidControllerTest.class.getSimpleName();

    private static URL NAVAID_RESOURCE;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        NAVAID_RESOURCE = addPathParams(baseUrl(), "navaid");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetNavaidsByIdent() throws Exception {
        URL url = addPathParams(NAVAID_RESOURCE, "ident");
        url = addPathParams(url, NAVAID_IDENT);
        LOG.info(url.toString());

        navaids(getForListObject(url, Navaid.class)).forEach(n -> {
            assert(n != null);
            assert(n.getIdent().equals(NAVAID_IDENT));
        });
    }

    @Test
    public void testGetNavaidsByIdent404() throws Exception {
        URL url = addPathParams(NAVAID_RESOURCE, "ident");
        url = addPathParams(url, BAD_NAVAID_IDENT);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 404;
    }

    @Test
    public void testGetNavaidsByStation() throws Exception {
        URL url = addPathParams(NAVAID_RESOURCE, "station");
        url = addPathParams(url, ICAO_CODE);
        LOG.info(url.toString());

        navaids(getForListObject(url, Navaid.class)).forEach(n -> {
            assert(n != null);
            assert(n.getAssociatedAirport().equals(ICAO_CODE));
        });
    }

    @Test
    public void testGetNavaidsByStation404() throws Exception {
        URL url = addPathParams(NAVAID_RESOURCE, "station");
        url = addPathParams(url, BAD_ICAO_CODE);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 404;
    }

    private static List<Navaid> navaids(List<Object> objects) {
        List<Navaid> navaids = new ArrayList<>(objects.size());
        objects.forEach(o -> navaids.add((Navaid)o));
        return navaids;
    }

}
