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
import com.grndctl.model.taf.Response;
import com.grndctl.model.taf.TAF;
import com.grndctl.model.taf.TimeType;
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
public class TafSvc extends AbstractSvc<com.grndctl.model.taf.Response> {
    
    private static final String NAME = TafSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(TafSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/"
            + "dataserver_current/httpparam?datasource=tafs&requesttype=retrieve"
            + "&format=xml";

    private static final String MOST_RECENT_CONSTRAINT = "&mostRecentForEachStation=constraint";

    private static final String STATION_STRING = "&stationString=";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    private static final String TIME_TYPE = "&timeType=";

    public TafSvc() {
        super(Response.class, NAME);
    }
    
    public List<TAF> getCurrentTaf(String station) throws ServiceException {
        try {
            URL url = new URL(RQST_URL + MOST_RECENT_CONSTRAINT + STATION_STRING + station + HRS_BEFORE + 1);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getTAF();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public List<TAF> getTafs(String station, double hrsBefore, TimeType timeType) throws ServiceException {
        try {
            URL url = new URL(RQST_URL + STATION_STRING + station + HRS_BEFORE + hrsBefore + TIME_TYPE + timeType.valueOf());
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getTAF();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}
