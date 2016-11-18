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
import com.grndctl.services.ChartsSvc;
import com.grndctl.services.StationSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/charts")
public class ChartsController {

    private static final String ICAO_CODE = "icaocode";

    private final StationSvc stationSvc;

    private final ChartsSvc chartsSvc;

    @Autowired
    public ChartsController(final StationSvc stationSvc, final ChartsSvc chartsSvc) {
        this.stationSvc = stationSvc;
        this.chartsSvc = chartsSvc;
    }

    @RequestMapping(value = "/{icaocode}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getStationCharts",
            nickname = "getStationCharts"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    public ResponseEntity<String> getStationCharts(@PathVariable(value = ICAO_CODE) final String icaoCode)
            throws ServiceException, ResourceNotFoundException {
        if (!stationSvc.stationExists(icaoCode, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", icaoCode));
        }

        return ResponseEntity.ok(chartsSvc.getStationCharts(icaoCode));
    }
}
