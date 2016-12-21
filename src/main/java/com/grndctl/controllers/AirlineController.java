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
