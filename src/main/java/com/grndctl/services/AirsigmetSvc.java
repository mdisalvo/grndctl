/**
 * This file is part of grndctl.
 *
 * grndctl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * grndctl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with grndctl.  If not, see <http://www.gnu.org/licenses/>.
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
public class AirsigmetSvc extends AbstractSvc<Response> {
    
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
