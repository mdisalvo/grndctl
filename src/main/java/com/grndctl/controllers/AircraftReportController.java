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

import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.aircraftrep.AircraftReport;
import com.grndctl.model.aircraftrep.ReportType;
import com.grndctl.services.AircraftReportSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
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

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(
            value = "getAircraftReports",
            nickname = "getAircraftReports",
            response = AircraftReport.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AircraftReport>> getAircraftReports(
            @RequestParam(value = HRS_BEFORE) double hrsBefore,
            @RequestParam(value = REPORT_TYPE, required = false) ReportType reportType) throws ServiceException {
        if (reportType == null) {
            reportType = ReportType.AIREP;
        }

        return ResponseEntity.ok(svc.getAircraftReports(hrsBefore, reportType));
    }

}
