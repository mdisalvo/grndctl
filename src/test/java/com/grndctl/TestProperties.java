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
package com.grndctl;

import java.net.URL;

import static java.lang.String.format;
import static java.lang.System.getProperty;

/**
 */
class TestProperties {

    protected static final String BEG_MSG;
    protected static final String END_MSG;

    protected static final String SERVER_PORT;
    protected static final String ICAO_CODE;
    protected static final String BAD_ICAO_CODE;
    protected static final String IATA_CODE;
    protected static final String BAD_IATA_CODE;
    protected static final String AIRLINE_ICAO;
    protected static final String BAD_AIRLINE_ICAO;
    protected static final String AIRLINE_IATA;
    protected static final String BAD_AIRLINE_IATA;

    protected static final URL BASE_URL;

    static {
        BEG_MSG = "Beginning Test: %s...";
        END_MSG = "Ending Test: %s...";

        SERVER_PORT = getProperty("server.port", "8888");
        ICAO_CODE = getProperty("icao.code", "KIAD");
        BAD_ICAO_CODE = getProperty("bad.icao.code", "XXXX");
        IATA_CODE = getProperty("iata.code", "IAD");
        BAD_IATA_CODE = getProperty("bad.iata.code", "XXX");
        AIRLINE_ICAO = getProperty("airline.icao", "UAL");
        BAD_AIRLINE_ICAO = getProperty("bad.airline.icao", "XXX");
        AIRLINE_IATA = getProperty("airline.iata", "UA");
        BAD_AIRLINE_IATA = getProperty("bad.airline.iata", "XX");

        try {
            BASE_URL = new URL(format("http://localhost:%s", SERVER_PORT));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
