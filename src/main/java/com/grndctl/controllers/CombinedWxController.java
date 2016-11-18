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
import com.grndctl.model.aggregates.CombinedWx;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.services.MetarSvc;
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

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/combinedwx")
public class CombinedWxController {

    private static final String STATION = "station";

    private static final String HRS_BEFORE = "hrsBefore";

    private MetarSvc metarSvc;
    private TafSvc tafSvc;
    private StationSvc stationSvc;

    @Autowired
    public CombinedWxController(final MetarSvc metarSvc, final TafSvc tafSvc, final StationSvc stationSvc) {
        this.metarSvc = metarSvc;
        this.tafSvc = tafSvc;
        this.stationSvc = stationSvc;
    }

    @RequestMapping(value = "/{station}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getCombinedWx",
            nickname = "getCombinedWx",
            response = CombinedWx.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    public ResponseEntity<CombinedWx> getCombinedWx(
            @PathVariable(value = STATION) String station,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "1.0") Double hrsBefore) throws
            ServiceException, ResourceNotFoundException {

        if (!stationSvc.stationExists(station, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with code %s does not exist.", station));
        }

        CombinedWx resp = new CombinedWx();
        resp.setMetars(metarSvc.getMetars(station, hrsBefore));
        resp.setTafs(tafSvc.getCurrentTaf(station));

        return ResponseEntity.ok(resp);
    }

}
