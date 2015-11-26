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
import com.grndctl.ServiceException;
import com.grndctl.model.station.Station;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.services.StationSvc;
import com.qmino.miredot.annotations.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Station information as provided by the NWS.
 *
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

    /**
     * Get information for a field such as latest <code>METAR</code> latest <code>TAF</code>, forecasts, etc.
     *
     * @param icaocode Station string
     * @return <code>List</code> of filtered <code>Station</code>s
     * @throws com.grndctl.ServiceException
     * @throws com.grndctl.ResourceNotFoundException
     */
    @RequestMapping(value = "/adds/{icaocode}", method = GET, produces = "application/json")
    @ReturnType(value = "java.util.List<com.grndctl.model.station.Station>")
    public ResponseEntity<List<Station>> getStationInfo(
            @PathVariable(value = ICAO_CODE) final String icaocode) throws ServiceException,
            ResourceNotFoundException {
        if (!stationSvc.stationExists(icaocode, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", icaocode));
        }

        return new ResponseEntity<>(stationSvc.getStationInfo(icaocode), HttpStatus.OK);
    }

    /**
     * Get FAA station status which includes basic wx, and delay information.
     *
     * Response Entity:
     * <pre>
     *     {
     *         "delay": "false",
     *         "IATA": "IAD",
     *         "state": "District of Columbia",
     *         "name": "Washington Dulles International",
     *         "weather": {
     *             "visibility": 10,
     *             "weather": "Mostly Cloudy",
     *             "meta": {
     *                 "credit": "NOAA's National Weather Service",
     *                 "updated": "9:52 PM Local",
     *                 "url": "http://weather.gov/"
     *             },
     *             "temp": "60.0 F (15.6 C)",
     *             "wind": "Northwest at 8.1mph"
     *         },
     *         "ICAO": "KIAD",
     *         "city" "Washington",
     *         "status": {
     *             "reason": "No known delays for this airport.",
     *             "closureBegin": "",
     *             "endTime": "",
     *             "minDelay": "",
     *             "avgDelay": "",
     *             "maxDelay": "",
     *             "closureEnd": "",
     *             "trend": "",
     *             "type": ""
     *         }
     *     }
     * </pre>
     *
     * @param iatacode IATA code for the aerodrome
     * @return A JSON <code>String</code> that is the FAA Airport Status Response
     * @throws com.grndctl.ServiceException
     * @throws com.grndctl.ResourceNotFoundException
     */
    @RequestMapping(value = "/faa/{iatacode}", method = GET, produces = "application/json")
    @ReturnType(value = "java.lang.String")
    public ResponseEntity<String> getFAAStationStatus(
            @PathVariable(value = IATA_CODE) final String iatacode) throws ServiceException, ResourceNotFoundException {

        if (!stationSvc.stationExists(iatacode, StationCodeType.IATA)) {
            throw new ResourceNotFoundException(String.format("Station with IATA code %s does not exist.", iatacode));
        }

        return new ResponseEntity<>(stationSvc.getFAAStationStatus(iatacode), HttpStatus.OK);
    }

}
