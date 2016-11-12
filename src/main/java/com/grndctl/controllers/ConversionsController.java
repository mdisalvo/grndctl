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
import org.apache.commons.lang3.math.Fraction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Simple conversion functions.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/conversions")
public class ConversionsController {

    public ConversionsController() { }

    /**
     * Convert Fahrenheit to Centigrade.
     *
     * @param tempF Temp. Fahrenheit (Ex. -> 75) [REQ'D]
     * @return F converted to Temp. Centigrade as a <code>double</code>
     */
    @RequestMapping(value = "/FtoC", method = GET, produces = "application/json")
    public ResponseEntity<ConversionResult> getFtoC(
            @RequestParam(value = "tempF") double tempF) {
        return ResponseEntity.ok(new ConversionResult((tempF - 32) * (Fraction.getFraction(5, 9).doubleValue()), "C"));
    }

    /**
     * Convert Centigrade to Fahrenheit.
     *
     * @param tempC Temp. Centigrade (Ex. -> 24) [REQ'D]
     * @return C converted to Temp. Fahrenheit as a <code>double</code>
     */
    @RequestMapping(value = "/CtoF", method = GET, produces = "application/json")
    public ResponseEntity<ConversionResult> getCtoF(
            @RequestParam(value = "tempC") double tempC) {
        return ResponseEntity.ok(new ConversionResult((tempC * 1.8 + 32), "F"));
    }

    /**
     * Convert inHg to mb.
     *
     * @param pressureInches Pressure in inHg to convert (Ex. -> 29.92) [REQ'D]
     * @return inHg converted to mb as a <code>double</code>
     */
    @RequestMapping(value = "/inchesToMillibars", method = GET, produces = "application/json")
    public ResponseEntity<ConversionResult> inchesToMillibars(
            @RequestParam(value = "pressInches") double pressureInches) {
        return ResponseEntity.ok(new ConversionResult((33.8639 * pressureInches), "mb"));
    }

    /**
     * Convert mb to inHg.
     *
     * @param pressureMillibars Pressure in mb to convert (Ex. -> 1013.2) [REQ'D]
     * @return mg converted to inHg as a <code>double</code>
     */
    @RequestMapping(value = "/millibarsToInches", method = GET, produces = "application/json")
    public ResponseEntity<ConversionResult> millibarsToInches(
            @RequestParam(value = "pressMillibars", defaultValue = "1013.2") final double pressureMillibars) {
        return ResponseEntity.ok(new ConversionResult(pressureMillibars / 33.8639, "inHg"));
    }

    /**
     * Get wind components (Negative HW is a tailwind component, and Negative XW from left).
     *
     * @param windSpeed Windspeed in KTS (Ex. -> 15.0) [REQ'D]
     * @param windDirection Wind direction (from) (Ex. -> 240.0) [REQ'D]
     * @param heading Current heading (Ex. -> 180.0) [REQ'D]
     * @return <code>WindComponent</code> entity that contains calculated components
     */
    @RequestMapping(value = "/windcomponent", method = GET, produces = "application/json")
    public ResponseEntity<WindComponent> getWindComponents(
            @RequestParam(value = "windspeed") double windSpeed,
            @RequestParam(value = "winddirection") double windDirection,
            @RequestParam(value = "heading") double heading) {
        return ResponseEntity.ok(new WindComponent(windSpeed, windDirection, heading));
    }
}
