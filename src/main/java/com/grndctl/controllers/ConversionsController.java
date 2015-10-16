package com.grndctl.controllers;

import org.apache.commons.lang3.math.Fraction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by michael on 10/16/15.
 */
@RestController
@RequestMapping("/conversions")
public class ConversionsController {

    private static Logger LOG = LogManager.getLogger(ConversionsController.class);

    public ConversionsController() {
    }

    @RequestMapping(value = "/FtoC", method = GET)
    public double getFtoC(
            @RequestParam(value = "tempF", defaultValue = "75") double tempF) {
        return (tempF - 32) * (Fraction.getFraction(5, 9).doubleValue());
    }

    @RequestMapping(value = "/CtoF", method = GET)
    public double getCtoF(
            @RequestParam(value = "tempC", defaultValue = "24") double tempC) {
        return (tempC * 1.8 + 32);
    }

    @RequestMapping(value = "/inchesToMillibars", method = GET)
    public double inchesToMillibars(
            @RequestParam(value = "pressInches", defaultValue = "29.92") double pressureInches) {
        return (33.8639 * pressureInches);
    }

    @RequestMapping(value = "/millibarsToInches", method = GET)
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
