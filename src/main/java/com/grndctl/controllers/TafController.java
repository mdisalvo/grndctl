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

import com.grndctl.exceptions.ResourceNotFoundException;
import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.model.taf.TAF;
import com.grndctl.model.taf.TimeType;
import com.grndctl.services.StationSvc;
import com.grndctl.services.TafSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/taf")
public class TafController {

    private static final String STATION = "station";

    private static final String TIME_TYPE = "timetype";

    private static final String HRS_BEFORE = "hrsBefore";

    private final TafSvc svc;

    private final StationSvc stationSvc;

    @Autowired
    public TafController(final TafSvc svc, final StationSvc stationSvc) {
        this.svc = svc;
        this.stationSvc = stationSvc;
    }

    @RequestMapping(value = "/{station}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getTafs",
            nickname = "getTafs",
            response = TAF.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<TAF>> getTafs(
            @PathVariable(value = STATION) String code,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "2.0") Double hrsBefore,
            @RequestParam(value = TIME_TYPE, required = false) TimeType timeType) throws ServiceException, ResourceNotFoundException {

        if (!stationSvc.stationExists(code, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", code));
        }

        if (timeType == null) {
            timeType = TimeType.VALID;
        }

        return ResponseEntity.ok(svc.getTafs(code, hrsBefore, timeType));
    }

}
