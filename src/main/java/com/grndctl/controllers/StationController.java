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

import com.grndctl.model.station.Station;
import com.grndctl.services.StationSvc;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String STATION = "station";

    private static final String IATA_CODE = "iatacode";

    private final StationSvc svc;

    @Autowired
    public StationController(final StationSvc svc) {
        this.svc = svc;
    }

    /**
     * Get information for a field such as latest <code>METAR</code> latest <code>TAF</code>, forecasts, etc.
     *
     * @param station Station string (Default -> KIAD)
     * @return <code>List</code> of filtered <code>Station</code>s
     * @throws Exception
     */
    @RequestMapping(value = "/adds", method = GET, produces = "application/json")
    public List<Station> getStationInfo(
            @RequestParam(value = STATION, defaultValue = "KIAD") final String station) throws Exception {
        return svc.getStationInfo(station);
    }

    /**
     * Get FAA station status which includes basic wx, and delay information.
     *
     * @param iatacode IATA code for the aerodrome (Default -> IAD)
     * @return A JSON <code>String</code> that is the FAA Airport Status Response
     * @throws Exception
     */
    @RequestMapping(value = "/faa", method = GET, produces = "application/json")
    public String getFAAStationStatus(
            @RequestParam(value = IATA_CODE, defaultValue = "IAD") final String iatacode) throws Exception {
        return svc.getFAAStationStatus(iatacode);
    }

}
