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
import com.grndctl.model.station.FaaStation;
import com.grndctl.model.station.Station;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.services.StationSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/station")
public class StationController {

    private static final String ICAO_CODE = "icaocode";

    private static final String IATA_CODE = "iatacode";

    private final StationSvc stationSvc;

    @Autowired
    public StationController(final StationSvc stationSvc) {
        this.stationSvc = stationSvc;
    }

    @RequestMapping(value = "/adds/{icaocode}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getStationInfo",
            nickname = "getStationInfo",
            response = Station.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Station>> getStationInfo(
            @PathVariable(value = ICAO_CODE) final String icaocode) throws ServiceException,
            ResourceNotFoundException {
        if (!stationSvc.stationExists(icaocode, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", icaocode));
        }

        return ResponseEntity.ok(stationSvc.getStationInfo(icaocode));
    }

    @RequestMapping(value = "/faa/{iatacode}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getFAAStationStatus",
            nickname = "getFAAStationStatus",
            response = FaaStation.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<FaaStation> getFAAStationStatus(
            @PathVariable(value = IATA_CODE) final String iatacode) throws ServiceException, ResourceNotFoundException {

        if (!stationSvc.stationExists(iatacode, StationCodeType.IATA)) {
            throw new ResourceNotFoundException(String.format("Station with IATA code %s does not exist.", iatacode));
        }

        return ResponseEntity.ok(stationSvc.getFAAStationStatus(iatacode));
    }

}
