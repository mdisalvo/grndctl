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

import com.google.common.io.CharStreams;
import com.grndctl.exceptions.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Michael Di Salvo
 */
@Service
public class ChartsSvc {

    private static final Logger LOG = LogManager.getLogger(ChartsSvc.class);

    private static final String AIRCHARTS_URL = "https://api.aircharts.org/v2/Airport/%s";

    private static final String THANKS_VALUE = "All information retrieved from AirCharts at http://www.aircharts.org";

    private static final String THANKS_KEY = "thanks";

    public ChartsSvc() { }

    public String getStationCharts(final String icaoCode) throws ServiceException {
        URL url;
        try {
            url = new URL(String.format(AIRCHARTS_URL, icaoCode.toUpperCase()));
            LOG.info(url.toString());
            try (InputStreamReader isr = new InputStreamReader(url.openStream())){
                return (new JSONObject(CharStreams.toString(isr)).put(THANKS_KEY, THANKS_VALUE)).toString();
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}
