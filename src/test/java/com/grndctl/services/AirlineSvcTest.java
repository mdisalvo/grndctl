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
import com.grndctl.model.misc.Airline;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.String.format;

/**
 */
public class AirlineSvcTest extends SvcTestSupport {

    private static final String TEST_NAME = AirlineSvcTest.class.getName();

    public AirlineSvcTest() {
        super();
    }

    @BeforeClass
    public static void setup() {
        LOG.info(format(BEG_MSG, TEST_NAME));
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void getAirlineByIcao() throws Exception {
        Airline airline = airlineSvc.getAirlineByIcao(AIRLINE_ICAO);
        assert airline.getIcao().equals(AIRLINE_ICAO);
        assert airline.getIata().equals(AIRLINE_IATA);
    }

    @Test
    public void getAirlineByIata() throws Exception {
        Airline airline = airlineSvc.getAirlineByIata(AIRLINE_IATA);
        assert airline.getIata().equals(AIRLINE_IATA);
        assert airline.getIcao().equals(AIRLINE_ICAO);
    }

    @Test
    public void getActiveAirlines() throws Exception {
        airlineSvc.getActiveAirlines().forEach(airline -> {
            assert airline.getActive().equals("Y");
        });
    }

    @Test
    public void getAllAirlines() throws Exception {
        airlineSvc.getAllAirlines().forEach(airline -> LOG.info(airline.getName() + ":" + airline.getIcao()));
    }
}
