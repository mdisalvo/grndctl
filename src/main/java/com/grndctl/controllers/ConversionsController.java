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

import com.grndctl.model.aggregates.ConversionResult;
import com.grndctl.model.aggregates.WindComponent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.math.Fraction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/conversions")
public class ConversionsController {

    private static final double CONV_FACTOR = 33.8639;

    public ConversionsController() { }

    @RequestMapping(value = "/FtoC", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getFtoC",
            nickname = "getFtoC",
            response = ConversionResult.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ConversionResult> getFtoC(
            @RequestParam(value = "tempF") double tempF) {
        return ResponseEntity.ok(new ConversionResult((tempF - 32) * (Fraction.getFraction(5, 9).doubleValue()), "C"));
    }

    @RequestMapping(value = "/CtoF", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getCtoF",
            nickname = "getCtoF",
            response = ConversionResult.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ConversionResult> getCtoF(
            @RequestParam(value = "tempC") double tempC) {
        return ResponseEntity.ok(new ConversionResult((tempC * 1.8 + 32), "F"));
    }

    @RequestMapping(value = "/inchesToMillibars", method = GET, produces = "application/json")
    @ApiOperation(
            value = "inchesToMillibars",
            nickname = "inchesToMillibars",
            response = ConversionResult.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ConversionResult> inchesToMillibars(
            @RequestParam(value = "pressInches") double pressureInches) {
        return ResponseEntity.ok(new ConversionResult((CONV_FACTOR * pressureInches), "mb"));
    }

    @RequestMapping(value = "/millibarsToInches", method = GET, produces = "application/json")
    @ApiOperation(
            value = "millibarsToInches",
            nickname = "millibarsToInches",
            response = ConversionResult.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ConversionResult> millibarsToInches(
            @RequestParam(value = "pressMillibars") final double pressureMillibars) {
        return ResponseEntity.ok(new ConversionResult(pressureMillibars / CONV_FACTOR, "inHg"));
    }

    @RequestMapping(value = "/windcomponent", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getWindComponents",
            nickname = "getWindComponents",
            response = WindComponent.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<WindComponent> getWindComponents(
            @RequestParam(value = "windspeed") double windSpeed,
            @RequestParam(value = "winddirection") double windDirection,
            @RequestParam(value = "heading") double heading) {
        return ResponseEntity.ok(new WindComponent(windSpeed, windDirection, heading));
    }
}
