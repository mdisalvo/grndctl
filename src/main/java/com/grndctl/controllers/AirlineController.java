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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Retrieve Airline information from data sourced by openflights.org.
 *
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

    /**
     * Retrieve all <code>Airline</code>s.
     *
     * All credit for data goes to the hardworking folk at openflights.org.
     *
     * @return A <code>List</code> of all <code>Airline</code>s in the openflights.org database
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
//    @ReturnType(value = "java.util.List<com.grndctl.model.misc.Airline>")
    @ApiOperation(value = "getAllAirlines", nickname = "getAllAirlines")
    public ResponseEntity<List<Airline>> getAllAirlines() {
        return ResponseEntity.ok(svc.getAllAirlines());
    }

    /**
     * Retrieve an <code>Airline</code> by <code>ICAO</code> code.
     *
     * All credit for data goes to the hardworking folk at openflights.org.
     *
     * @param icao The ICAO identifier of the <code>Airline</code> to retrieve [REQ'D]
     * @return Requested <code>Airline</code>
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/icao/{icao}", method = GET, produces = "application/json")
    public ResponseEntity<Airline> getAirlineByIcao(@PathVariable String icao) throws ResourceNotFoundException {
        Airline a = svc.getAirlineByIcao(icao.toUpperCase());

        if (a == null) {
            throw new ResourceNotFoundException(String.format("Airline with ICAO code %s does not exist.", icao));
        }

        return ResponseEntity.ok(a);
    }

    /**
     * Retrieve an <code>Airline</code> by <code>IATA</code> code.
     *
     * All credit for data goes to the hardworking folk at openflights.org.
     *
     * @param iata The IATA identifier of the <code>Airline</code> to retrieve [REQ'D]
     * @return Requested <code>Airline</code>
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/iata/{iata}", method = GET, produces = "application/json")
    public ResponseEntity<Airline> getAirlineByIata(@PathVariable String iata) throws ResourceNotFoundException {
        Airline a = svc.getAirlineByIata(iata.toUpperCase());

        if (a == null) {
            throw new ResourceNotFoundException(String.format("Airline with IATA code %s does not exist.", iata));
        }

        return ResponseEntity.ok(a);
    }

    /**
     * Retrieve all active <code>Airline</code>s.
     *
     * All credit for data goes to the hardworking folk at openflights.org.
     *
     * @return A <code>List</code> of all active airlines in the openflights.org database
     */
    @RequestMapping(value = "/active", method = GET, produces = "application/json")
    public ResponseEntity<List<Airline>> getActiveAirlines() {
        return ResponseEntity.ok(svc.getActiveAirlines());
    }

}
