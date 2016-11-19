/**
 * MIT License
 *
 * Copyright (c) 2016 grndctl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
