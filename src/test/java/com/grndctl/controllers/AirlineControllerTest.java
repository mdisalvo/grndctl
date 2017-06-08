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
import com.grndctl.model.misc.Airline;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 */
public class AirlineControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = AirlineControllerTest.class.getSimpleName();

    private static URL AIRLINE_RESOURCE;

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
        AIRLINE_RESOURCE = addPathParams(baseUrl(), "airline");
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetAllAirlines() throws Exception {
        assert (!airlines(getForListObject(AIRLINE_RESOURCE, Airline.class)).isEmpty());
    }

    @Test
    public void testGetAirlineIcao() throws Exception {
        URL url = addPathParams(AIRLINE_RESOURCE, "icao");
        url = addPathParams(url, AIRLINE_ICAO);
        LOG.info(url.toString());

        Airline airline = rest().getForObject(url.toExternalForm(), Airline.class);
        assert (airline.getIcao().equals(AIRLINE_ICAO));
        assert (airline.getIata().equals(AIRLINE_IATA));
    }

    @Test
    public void testGetAirlineIcao404() throws Exception {
        URL url = addPathParams(AIRLINE_RESOURCE, "icao");
        url = addPathParams(url, BAD_AIRLINE_ICAO);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 404;
    }

    @Test
    public void testGetAirlineIata() throws Exception {
        URL url = addPathParams(AIRLINE_RESOURCE, "iata");
        url = addPathParams(url, AIRLINE_IATA);
        LOG.info(url.toString());

        Airline airline = rest().getForObject(url.toExternalForm(), Airline.class);
        assert (airline.getIcao().equals(AIRLINE_ICAO));
        assert (airline.getIata().equals(AIRLINE_IATA));
    }

    @Test
    public void testGetAirlineIata404() throws Exception {
        URL url = addPathParams(AIRLINE_RESOURCE, "iata");
        url = addPathParams(url, BAD_AIRLINE_IATA);
        LOG.info(url.toString());

        ExceptionModel e = rest().getForObject(url.toExternalForm(), ExceptionModel.class);

        assert e.getStatus() == 404;
    }

    @Test
    public void testGetAllActiveAirlines() throws Exception {
        URL url = addPathParams(AIRLINE_RESOURCE, "active");
        LOG.info(url.toString());

        airlines(getForListObject(url, Airline.class)).forEach(a -> {
            LOG.info(a.getName() + ":" + a.getActive());
            assert (a.getActive().equals("Y"));
        });
    }

    private static List<Airline> airlines(List<Object> objects) {
        List<Airline> airlines = new ArrayList<>(objects.size());
        objects.forEach(o -> airlines.add((Airline)o));
        return airlines;
    }

}
