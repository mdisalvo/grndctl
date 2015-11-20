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

import com.grndctl.ServiceException;
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
public class MetarSvc extends AbstractSvc<Response> {
    
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
