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
import com.grndctl.model.flightplan.Notam;
import com.grndctl.services.NotamSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/notam")
public class NotamController {

    private static final String CODES = "codes";

    private static final String REPORT_TYPE = "reportType";

    private static final String FORMAT_TYPE = "formatType";

    private final NotamSvc svc;

    @Autowired
    public NotamController(final NotamSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getNotamsForCodes",
            nickname = "getNotamsForCodes",
            response = String.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<String>> getNotamsForCodes(
            @RequestParam(value = CODES) List<String> codes,
            @RequestParam(value = REPORT_TYPE, required = false) Notam.ReportType reportType,
            @RequestParam(value = FORMAT_TYPE, required = false) Notam.FormatType formatType) throws ServiceException {

        if (reportType == null) {
            reportType = Notam.ReportType.RAW;
        }

        if (formatType == null) {
            formatType = Notam.FormatType.DOMESTIC;
        }

        return ResponseEntity.ok(svc.getNotamsForCodes(codes, reportType, formatType));
    }

}
