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
import com.grndctl.model.misc.Airline;
import com.grndctl.services.AirlineSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/airline")
public class AirlineController {

    private final AirlineSvc svc;

    @Autowired
    public AirlineController(final AirlineSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAllAirlines",
            nickname = "getAllAirlines",
            response = Airline.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    public ResponseEntity<List<Airline>> getAllAirlines() {
        return ResponseEntity.ok(svc.getAllAirlines());
    }

    @RequestMapping(value = "/icao/{icao}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAirlineByIcao",
            nickname = "getAirlineByIcao",
            response = Airline.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    public ResponseEntity<Airline> getAirlineByIcao(@PathVariable String icao) throws ResourceNotFoundException {
        Airline a = svc.getAirlineByIcao(icao.toUpperCase());

        if (!nonNull(a)) {
            throw new ResourceNotFoundException(String.format("Airline with ICAO code %s does not exist.", icao));
        }

        return ResponseEntity.ok(a);
    }

    @RequestMapping(value = "/iata/{iata}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAirlineByIata",
            nickname = "getAirlineByIata",
            response = Airline.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Resource Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    public ResponseEntity<Airline> getAirlineByIata(@PathVariable String iata) throws ResourceNotFoundException {
        Airline a = svc.getAirlineByIata(iata.toUpperCase());

        if (!nonNull(a)) {
            throw new ResourceNotFoundException(String.format("Airline with IATA code %s does not exist.", iata));
        }

        return ResponseEntity.ok(a);
    }

    @ApiOperation(
            value = "getAllActiveAirlines",
            nickname = "getAllActiveAirlines",
            response = Airline.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    @RequestMapping(value = "/active", method = GET, produces = "application/json")
    public ResponseEntity<List<Airline>> getActiveAirlines() {
        return ResponseEntity.ok(svc.getActiveAirlines());
    }

}
