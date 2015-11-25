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

import com.grndctl.model.aggregates.WindComponent;
import org.apache.commons.lang3.math.Fraction;
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
     * @param tempF Temp. Fahrenheit (Default -> 75)
     * @return F converted to Temp. Centigrade as a <code>double</code>
     */
    @RequestMapping(value = "/FtoC", method = GET, produces = "application/json")
    public double getFtoC(
            @RequestParam(value = "tempF", defaultValue = "75") double tempF) {
        return (tempF - 32) * (Fraction.getFraction(5, 9).doubleValue());
    }

    /**
     * Convert Centigrade to Fahrenheit.
     *
     * @param tempC Temp. Centigrade (Default -> 24)
     * @return C converted to Temp. Fahrenheit as a <code>double</code>
     */
    @RequestMapping(value = "/CtoF", method = GET, produces = "application/json")
    public double getCtoF(
            @RequestParam(value = "tempC", defaultValue = "24") double tempC) {
        return (tempC * 1.8 + 32);
    }

    /**
     * Convert inHg to mb.
     *
     * @param pressureInches Pressure in inHg to convert (Default -> 29.92)
     * @return inHg converted to mb as a <code>double</code>
     */
    @RequestMapping(value = "/inchesToMillibars", method = GET, produces = "application/json")
    public double inchesToMillibars(
            @RequestParam(value = "pressInches", defaultValue = "29.92") double pressureInches) {
        return (33.8639 * pressureInches);
    }

    /**
     * Convert mb to inHg.
     *
     * @param pressureMillibars Pressure in mb to convert (Default -> 1013.2)
     * @return mg converted to inHg as a <code>double</code>
     */
    @RequestMapping(value = "/millibarsToInches", method = GET, produces = "application/json")
    public double millibarsToInches(
            @RequestParam(value = "pressMillibars", defaultValue = "1013.2") final double pressureMillibars) {
        return (pressureMillibars / 33.8639);
    }

    /**
     * Get wind components (Negative HW is a tailwind component, and Negative XW from left).
     *
     * @param windSpeed Windspeed in KTS (Default -> 15.0)
     * @param windDirection Wind direction (from) (Default -> 240.0)
     * @param heading Current heading (Default -> 180.0)
     * @return <code>WindComponent</code> entity that contains calculated components
     */
    @RequestMapping(value = "/windcomponent", method = GET, produces = "application/json")
    public WindComponent getWindComponents(
            @RequestParam(value = "windspeed", defaultValue = "15.0") double windSpeed,
            @RequestParam(value = "winddirection", defaultValue = "240.0") double windDirection,
            @RequestParam(value = "heading", defaultValue = "180.0") double heading) {
        return new WindComponent(windSpeed, windDirection, heading);
    }
}
