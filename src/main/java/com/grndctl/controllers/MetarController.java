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
import com.grndctl.model.metar.METAR;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.services.MetarSvc;
import com.grndctl.services.StationSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * {@link METAR}s for a station.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/metar")
public class MetarController extends AbstractController {

    private static final String STATION = "station";

    private static final String HRS_BEFORE = "hrsBefore";

    private final MetarSvc svc;

    private final StationSvc stationSvc;

    @Autowired
    public MetarController(final MetarSvc svc, final StationSvc stationSvc) {
        this.svc = svc;
        this.stationSvc = stationSvc;
    }

    /**
     * <code>METAR</code>s for a station.  <code>hrsBefore</code> parameter is provided to retrieve historical reports.
     *
     * @param station Station string (Default -> KIAD)
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @return <code>List</code> of filtered <code>METAR</code>s
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<METAR> getMetar(
            @RequestParam(value = STATION, defaultValue = "KIAD") String station,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "1.0") Double hrsBefore) throws
            ServiceException, ResourceNotFoundException {

        if (!stationSvc.stationExists(station, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", station));
        }

        if (hrsBefore == null)
            return svc.getCurrentMetar(station);
        else
            return svc.getMetars(station, hrsBefore);
    }

}
