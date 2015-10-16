package com.grndctl.controllers;

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
     * @param tempF Temp. Fahrenheit
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
     * @param tempC Temp. Centigrade
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
     * @param pressureInches Pressure in inHg to convert
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
     * @param pressureMillibars Pressure in mb to conver
     * @return mg converted to inHg as a <code>double</code>
     */
    @RequestMapping(value = "/millibarsToInches", method = GET, produces = "application/json")
    public double millibarsToInches(
            @RequestParam(value = "pressMillibars", defaultValue = "1013.2") final double pressureMillibars) {
        return (pressureMillibars / 33.8639);
    }

//    @RequestMapping(value = "/windcomponent", method = GET)
//    public WindComponent getWindComponents(
//            @RequestParam(value = "windspeed") double windSpeed,
//            @RequestParam(value = "winddirection") int windDirection,
//            @RequestParam(value = "heading") double heading) {
//
//        LOG.info("GET /grndctl/conversions/windcomponent?windspeed={}" +
//                "&winddirection={}&heading={}", windSpeed, windDirection, heading);
//
//        WindComponent results = new WindComponent(windSpeed, windDirection, heading);
//
//        return results;
//    }
}
