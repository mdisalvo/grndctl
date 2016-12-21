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
import com.grndctl.model.airsigmet.AIRSIGMET;
import com.grndctl.model.airsigmet.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Michael Di Salvo
 */
@Service
public class AirsigmetSvc extends AbstractSvc<com.grndctl.model.airsigmet.Response> {
    
    private static final String NAME = AirsigmetSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(AirsigmetSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/" +
            "dataserver_current/httpparam?datasource=airsigmets&requesttype=retrieve" +
            "&format=xml";

    private static final String MOST_RECENT_CONSTRAINT = "&mostRecent=true";

    private static final String MIN_ALTITUDE_FT = "&minAltitudeFt=";

    private static final String MAX_ALTITUDE_FT = "&maxAltitudeFt=";

    private static final String MIN_LAT = "&minLat=";

    private static final String MAX_LAT = "&maxLat=";

    private static final String MIN_LON = "&minLon=";

    private static final String MAX_LON = "&maxLon=";

    private static final String HOURS_BEFORE_NOW = "&hoursBeforeNow=";

    private static final String START_TIME = "&startTime=";

    private static final String END_TIME = "&endTime=";

    private static final String MIDNIGHT_Z = "T00:00:00Z";
    
    public AirsigmetSvc() {
        super(Response.class, NAME);
    }
    
    public List<AIRSIGMET> getAirsigmets() throws ServiceException {
        String[] times = startAndEndTimes();
        try {
            URL url = new URL(RQST_URL + MOST_RECENT_CONSTRAINT + START_TIME + times[0] + END_TIME + times[1]);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getAIRSIGMET();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public List<AIRSIGMET> getAirsigmetsHoursBeforeNow(double hoursBeforeNow) throws ServiceException {
        try {
            URL url = new URL(RQST_URL + HOURS_BEFORE_NOW + hoursBeforeNow);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getAIRSIGMET();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public List<AIRSIGMET> getAirsigmetsInAltitudeRange(int minAltFt, int maxAltFt, double hrsBeforeNow)
            throws ServiceException {
        try {
            URL url = new URL(RQST_URL + MIN_ALTITUDE_FT + minAltFt + MAX_ALTITUDE_FT + maxAltFt + HOURS_BEFORE_NOW + hrsBeforeNow);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getAIRSIGMET();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public List<AIRSIGMET> getAirsigmetsInLatLongRectangle(int minLat, int minLong, int maxLat, int maxLong,
                                                           double hrsBeforeNow) throws ServiceException {
        try {
            URL url = new URL(
                    RQST_URL + MIN_LON + minLong + MAX_LON + maxLong + MIN_LAT + minLat + MAX_LAT + maxLat + HOURS_BEFORE_NOW + hrsBeforeNow
            );
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getAIRSIGMET();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    private static String[] startAndEndTimes() {
        String[] dates = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        dates[0] = (sdf.format(cal.getTime()) + MIDNIGHT_Z);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        dates[1] = (sdf.format(cal.getTime()) + MIDNIGHT_Z);

        return dates;
    }

}
