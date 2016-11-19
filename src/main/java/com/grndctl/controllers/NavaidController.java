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
import com.grndctl.model.flightplan.Navaid;
import com.grndctl.services.NavaidSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/navaid")
public class NavaidController {

    private final NavaidSvc svc;

    @Autowired
    public NavaidController(final NavaidSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAllNavaidsByIdent",
            nickname = "getAllNavaidsByIdent",
            response = Navaid.class,
            responseContainer = "Map"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Collection<Navaid>>> getAllNavaidsByIdent() {
        return ResponseEntity.ok(svc.getAllNavaidsByIdent());
    }

    @RequestMapping(value = "/ident/{ident}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getNavaidsByIdent",
            nickname = "getNavaidsByIdent",
            response = Navaid.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Navaid>> getNavaidsByIdent(@PathVariable String ident) throws ResourceNotFoundException {
        List<Navaid> response = svc.getNavaidByIdent(ident.toUpperCase());

        if (response.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No Navaids with ident %s exist.", ident));
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/station/{station}", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getNavaidsByStation",
            nickname = "getNavaidsByStation",
            response = Navaid.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Navaid>> getNavaidsByStation(@PathVariable String station)
            throws ResourceNotFoundException {
        List<Navaid> response = svc.getNavaidsByAssociatedAirport(station.toUpperCase());

        if (response.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No Navaids for station %s exist.", station));
        }

        return ResponseEntity.ok(response);
    }

}
