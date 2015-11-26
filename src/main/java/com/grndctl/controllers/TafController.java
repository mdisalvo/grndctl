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
import com.grndctl.model.station.StationCodeType;
import com.grndctl.model.taf.TAF;
import com.grndctl.model.taf.TimeType;
import com.grndctl.services.StationSvc;
import com.grndctl.services.TafSvc;
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
 * Terminal Aerodrome Forecasts as issued by the NWS.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/taf")
public class TafController {

    private static final String STATION = "station";

    private static final String TIME_TYPE = "timetype";

    private static final String HRS_BEFORE = "hrsBefore";

    private final TafSvc svc;

    private final StationSvc stationSvc;

    @Autowired
    public TafController(final TafSvc svc, final StationSvc stationSvc) {
        this.svc = svc;
        this.stationSvc = stationSvc;
    }

    /**
     * Retrieve <code>TAF</code>s hours before now for a station by either issue time or validity.  If
     * <code>timeType</code> is null, then <code>TimeType.VALID</code> will be passed to the service.
     *
     * @param code Station string
     * @param hrsBefore Hours before now (Default -> 2.0)
     * @param timeType <code>ISSUE</code> or (Default)<code>VALID</code>
     * @return <code>List</code> of filtered <code>TAF</code>s
     * @throws com.grndctl.ServiceException
     * @throws com.grndctl.ResourceNotFoundException
     */
    @RequestMapping(value = "/{station}", method = GET, produces = "application/json")
    @ReturnType(value = "java.util.List<com.grndctl.model.taf.TAF>")
    public ResponseEntity<List<TAF>> getTafs(
            @PathVariable(value = STATION) String code,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "2.0") Double hrsBefore,
            @RequestParam(value = TIME_TYPE, required = false) TimeType timeType) throws ServiceException, ResourceNotFoundException {

        if (!stationSvc.stationExists(code, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", code));
        }

        if (timeType == null) {
            timeType = TimeType.VALID;
        }

        return new ResponseEntity<>(svc.getTafs(code, hrsBefore, timeType), HttpStatus.OK);
    }

}
