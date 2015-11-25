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

import com.grndctl.ServiceException;
import com.grndctl.model.flightplan.ValidationResults;
import com.grndctl.services.IntlFPValidationSvc;
import com.qmino.miredot.annotations.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Basic service to validate an ICAO international flight plan String.
 *
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/intlfpvalidator")
public class IntlFPValidationController extends AbstractController {

    private final IntlFPValidationSvc svc;

    @Autowired
    public IntlFPValidationController(final IntlFPValidationSvc svc) {
        this.svc = svc;
    }

    /**
     * Accepts an ICAO flight plan as a <code>String</code> to validate.
     *
     * <pre>
     *
     * (FPL-DLH560_IS
     * -1A319/M-SHWY/S
     * -EGLL0600
     * -N0420F370 BPK UQ295 CLN UL620 ARTOV UP44 SOMVA UP155 OKOKO UZ303 DHE UP729 DOSUR P729 LUGAS
     * -EKCH0715 ESMS)
     *
     * </pre>
     *
     * @param flightPlan ICAO flight plan to validate (Default ^)
     * @return <code>ValidationResults</code> object that holds the original flight plan, and the returned messages.
     * @throws com.grndctl.ServiceException
     */
    @RequestMapping(value = "/validate", method = POST, consumes = "application/json", produces = "application/json")
    @ReturnType(value = "com.grndctl.model.flightplan.ValidationResults")
    public ResponseEntity<ValidationResults> validateFlightPlan(
            @RequestBody(required = true) String flightPlan) throws ServiceException {
        return new ResponseEntity<>(svc.validateFlightPlan(flightPlan), HttpStatus.OK);
    }

}
