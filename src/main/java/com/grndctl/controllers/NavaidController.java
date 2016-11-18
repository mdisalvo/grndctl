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
