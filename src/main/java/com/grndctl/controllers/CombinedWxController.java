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

import com.grndctl.model.aggregates.CombinedWx;
import com.grndctl.services.MetarSvc;
import com.grndctl.services.TafSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Combines the {@link MetarSvc} with the {@link TafSvc} to get the combined meteorological conditions for an aerodrome.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/combinedwx")
public class CombinedWxController {

    private static final String STATION = "station";

    private static final String HRS_BEFORE = "hrsBefore";

    private MetarSvc metarSvc;
    private TafSvc tafSvc;

    @Autowired
    public CombinedWxController(final MetarSvc metarSvc, final TafSvc tafSvc) {
        this.metarSvc = metarSvc;
        this.tafSvc = tafSvc;
    }

    /**
     * The combined conditions for a field. Takes a string parameter for the field to retrieve, and hours before now.
     *
     * @param station Station string (Default -> KIAD)
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @return <code>CombinedWx</code> entity
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public CombinedWx getCombinedWx(
            @RequestParam(value = STATION, defaultValue = "KIAD") String station,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "1.0") Double hrsBefore) throws Exception {

        CombinedWx resp = new CombinedWx();
        resp.setMetars(metarSvc.getMetars(station, (hrsBefore == null || hrsBefore < 1.0 ? 1.0 : hrsBefore)));
        resp.setTafs(tafSvc.getCurrentTaf(station));

        return resp;
    }

}
