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

import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.pirep.PIREP;
import com.grndctl.services.PirepSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller to retrieve {@link PIREP}s issued by the NWS.
 *
 * @author Michael Di Salvo
 */
@Deprecated
@RestController
@RequestMapping(value = "/pirep")
public class PirepController {

    private static final String HRS_BEFORE = "hrsBefore";

    private final PirepSvc svc;

    @Autowired
    public PirepController(final PirepSvc svc) {
        this.svc = svc;
    }

    /**
     * [DEPRECATED] Get the reps.  The <code>PIREP</code>s.
     *
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @return <code>List</code> of filtered <code>PIREP</code>s
     * @throws ServiceException
     */
    @Deprecated
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public ResponseEntity<List<PIREP>> getPireps(
            @RequestParam(value = HRS_BEFORE, defaultValue = "1.0") double hrsBefore) throws ServiceException {
        return ResponseEntity.ok(svc.getPireps(hrsBefore));
    }
}
