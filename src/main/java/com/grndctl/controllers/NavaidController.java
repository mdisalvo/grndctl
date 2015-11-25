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

import com.grndctl.ResourceNotFoundException;
import com.grndctl.model.flightplan.Navaid;
import com.grndctl.services.NavaidSvc;
import com.qmino.miredot.annotations.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/navaid")
public class NavaidController extends AbstractController {

    private final NavaidSvc svc;

    @Autowired
    public NavaidController(final NavaidSvc svc) {
        this.svc = svc;
    }

    /**
     * Retrieve all <code>Navaid</code>s.
     *
     * @return <code>List</code> of <code>Navaid</code>s
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ReturnType(value = "java.util.Map<java.lang.String, java.util.Collection<com.grndctl.model.flightplan.Navaid>>")
    public ResponseEntity<Map<String, Collection<Navaid>>> getAllNavaidsByIdent() {
        return new ResponseEntity<>(svc.getAllNavaidsByIdent(), HttpStatus.OK);
    }

    /**
     * Retrieve <code>Navaid</code>s associated with an identifier.
     *
     * @param ident The identifier of the <code>Navaid</code>(s) to return
     * @return <code>List</code> of filtered <code>Navaid</code>s
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/ident/{ident}", method = GET, produces = "application/json")
    @ReturnType(value = "java.util.List<com.grndctl.model.flightplan.Navaid>")
    public ResponseEntity<List<Navaid>> getNavaidsByIdent(@PathVariable String ident) throws ResourceNotFoundException {
        List<Navaid> response = svc.getNavaidByIdent(ident);

        if (response.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No Navaids with ident %s exist.", ident));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieve <code>Navaid</code>s associate with an ICAO station code.
     *
     * @param station The ICAO station code of the associated <code>Navaid</code>(s) to return
     * @return <code>List</code> of filtered <code>Navaid</code>s
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/station/{station}", method = GET, produces = "application/json")
    @ReturnType(value = "java.util.List<com.grndctl.model.flightplan.Navaid>")
    public ResponseEntity<List<Navaid>> getNavaidsByStation(@PathVariable String station) throws ResourceNotFoundException {
        List<Navaid> response = svc.getNavaidsByAssociatedAirport(station);

        if (response.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No Navaids for station %s exist.", station));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
