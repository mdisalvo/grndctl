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

import com.google.common.io.CharStreams;
import com.grndctl.model.station.Response;
import com.grndctl.model.station.Station;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * 
 * @author Michael Di Salvo
 */
@Service
public class StationSvc extends AbstractSvc<Response> {
    
    private static final String NAME = StationSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(StationSvc.class);

    private static final String ADDS_RQST_URL = "https://aviationweather.gov/adds/"
            + "dataserver_current/httpparam?datasource=stations&requesttype=retrieve"
            + "&format=xml";

    private static final String FAA_RQST_URL = "http://services.faa.gov/airport/status/";

    private static final String RQST_FORMAT_JSON = "?format=application/json";

    private static final String STATION_STRING = "&stationString=";

    public StationSvc() {
        super(Response.class, NAME);
    }
    
    public List<Station> getStationInfo(String code) throws Exception {
        URL url = new URL(ADDS_RQST_URL + STATION_STRING + code.toUpperCase());
        LOG.info(url.toString());

        return unmarshall(url.openStream()).getData().getStation();
    }

    public String getFAAStationStatus(String iataCode) throws Exception {
        URL url = new URL(FAA_RQST_URL + iataCode + RQST_FORMAT_JSON);
        LOG.info(url.toString());

        return CharStreams.toString(new InputStreamReader(url.openStream()));
    }

}
