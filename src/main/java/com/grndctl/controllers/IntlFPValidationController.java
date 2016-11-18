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
import com.grndctl.model.flightplan.ValidationResults;
import com.grndctl.services.IntlFPValidationSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/intlfpvalidator")
public class IntlFPValidationController {

    private final IntlFPValidationSvc svc;

    @Autowired
    public IntlFPValidationController(final IntlFPValidationSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "/validate", method = POST, consumes = "application/json", produces = "application/json")
    @ApiOperation(
            value = "validateFlightPlan",
            nickname = "validateFlightPlan",
            response = ValidationResults.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ValidationResults> validateFlightPlan(
            @RequestBody String flightPlan) throws ServiceException {
        return ResponseEntity.ok(svc.validateFlightPlan(flightPlan));
    }

}
