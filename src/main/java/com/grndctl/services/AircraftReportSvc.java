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
import com.grndctl.model.aircraftrep.AircraftReport;
import com.grndctl.model.aircraftrep.ReportType;
import com.grndctl.model.aircraftrep.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Michael Di Salvo
 */
@Service
public class AircraftReportSvc extends AbstractSvc<com.grndctl.model.aircraftrep.Response> {
    
    private static final String NAME = AircraftReportSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(AircraftReportSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/" +
            "dataserver_current/httpparam?datasource=aircraftreports&" +
            "requesttype=retrieve&format=xml";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";
    
    public AircraftReportSvc() {
        super(Response.class, NAME);
    }

    @Cacheable("aircraftreps")
    public List<AircraftReport> getAircraftReports(final double hrsBefore, final ReportType reportType)
            throws ServiceException {
        try {
            URL url = new URL(RQST_URL + HRS_BEFORE + hrsBefore);
            LOG.info(url.toString());

            return unmarshall(url.openStream())
                    .getData()
                    .getAircraftReport()
                    .stream()
                    .filter(airep -> airep.getReportType().equals(reportType.toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
    
}
