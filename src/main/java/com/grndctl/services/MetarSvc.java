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
package com.grndctl.services;

import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.metar.METAR;
import com.grndctl.model.metar.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * 
 * @author Michael Di Salvo
 */
@Service
public class MetarSvc extends AbstractSvc<com.grndctl.model.metar.Response> {
    
    private static final String NAME = MetarSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(MetarSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/dataserver_current/httpparam?" +
            "dataSource=metars&requestType=retrieve&format=xml";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    private static final String STATION_STRING = "&stationString=";

    private static final String MOST_RECENT_CONSTRAINT = "&mostRecentForEachStation=constraint";

    private static final double ONE_HR = 1.0;

    public MetarSvc() {
        super(Response.class, NAME);
    }

    public List<METAR> getCurrentMetar(String station) throws ServiceException {
        try {
            URL url = new URL(RQST_URL + MOST_RECENT_CONSTRAINT + STATION_STRING + station + HRS_BEFORE + ONE_HR);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getMETAR();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public List<METAR> getMetars(String station, double hrsBefore) throws ServiceException {
        try {
            URL url = new URL(RQST_URL + HRS_BEFORE + hrsBefore + STATION_STRING + station);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getMETAR();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}
