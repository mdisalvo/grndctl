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

import com.grndctl.model.taf.Response;
import com.grndctl.model.taf.TAF;
import com.grndctl.model.taf.TimeType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

/**
 * 
 * @author Michael Di Salvo
 */
@Service
public class TafSvc extends AbstractSvc<Response> {
    
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
    
    public List<TAF> getCurrentTaf(String station) throws Exception {
        URL url = new URL(RQST_URL + MOST_RECENT_CONSTRAINT + STATION_STRING + station + HRS_BEFORE + 1);
        LOG.info(url.toString());

        return unmarshall(url.openStream()).getData().getTAF();
    }

    public List<TAF> getTafs(String station, double hrsBefore, TimeType timeType) throws Exception {
        URL url = new URL(RQST_URL + STATION_STRING + station + HRS_BEFORE + hrsBefore + TIME_TYPE + timeType.valueOf());
        LOG.info(url.toString());

        return unmarshall(url.openStream()).getData().getTAF();
    }

}
