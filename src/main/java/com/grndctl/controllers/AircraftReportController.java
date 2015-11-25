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
package com.grndctl.controllers;

import com.grndctl.ServiceException;
import com.grndctl.model.aircraftrep.AircraftReport;
import com.grndctl.model.aircraftrep.ReportType;
import com.grndctl.services.AircraftReportSvc;
import com.qmino.miredot.annotations.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller to retrieve {@link com.grndctl.model.aircraftrep.AircraftReport}s issued by the NWS.
 *
 * This replaces the {@link com.grndctl.controllers.PirepController}, a service that has been deprecated by the NWS.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping(value = "/aircraftrep")
public class AircraftReportController {

    private static final String HRS_BEFORE = "hrsBefore";

    private static final String REPORT_TYPE = "reportType";

    private final AircraftReportSvc svc;

    @Autowired
    public AircraftReportController(final AircraftReportSvc svc) {
        this.svc = svc;
    }

    /**
     * Get the reports.  The <code>AircraftReport</code>s.
     *
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @param reportType The {@link com.grndctl.model.aircraftrep.ReportType} to return (Default -> AIREP)
     * @return <code>List</code> of filtered <code>AircraftReport</code>s
     * @throws com.grndctl.ServiceException
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ReturnType(value = "java.util.List<com.grndctl.model.aircraftrep.AircraftReport>")
    public ResponseEntity<List<AircraftReport>> getAircraftReports(
            @RequestParam(value = HRS_BEFORE, defaultValue = "1.0") double hrsBefore,
            @RequestParam(value = REPORT_TYPE, required = false) ReportType reportType) throws ServiceException {
        if (reportType == null) {
            reportType = ReportType.AIREP;
        }

        return new ResponseEntity<>(svc.getAircraftReports(hrsBefore, reportType), HttpStatus.OK);
    }

}
