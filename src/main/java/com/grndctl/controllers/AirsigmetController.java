package com.grndctl.controllers;

import com.grndctl.model.airsigmet.AIRSIGMET;
import com.grndctl.services.AirsigmetSvc;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
/**
 * Created by michael on 10/16/15.
 */
@RestController
@RequestMapping("/airsigmet")
public class AirsigmetController {

    private static final Logger LOG = LogManager.getLogger(AirsigmetController.class);
    private static final String HRS_BEFORE = "hrsBefore";
    private static final String MAX_ALT_FT = "maxAltitudeFt";
    private static final String MIN_ALT_FT = "minAltitudeFt";
    private static final String MIN_LAT = "minLat";
    private static final String MAX_LAT = "maxLat";
    private static final String MIN_LON = "minLon";
    private static final String MAX_LON = "maxLon";

    private final AirsigmetSvc svc;

    @Autowired
    public AirsigmetController(final AirsigmetSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, consumes = "application/json", produces = "application/json")
    public List<AIRSIGMET> getAirsigmets() throws Exception{
        return svc.getAirsigmets();
    }

    @RequestMapping(value = "/altLimited", method = GET, consumes = "application/json", produces = "application/json")
    public List<AIRSIGMET> getAirsigmetsByAlt(
            @RequestParam(value = HRS_BEFORE, required = true, defaultValue = "1.0") double hoursBefore,
            @RequestParam(value = MIN_ALT_FT, required = true, defaultValue = "5000") int minAltFt,
            @RequestParam(value = MAX_ALT_FT, required = true, defaultValue = "30000") int maxAltFt) throws  Exception {
        return svc.getAirsigmetsInAltitudeRange(minAltFt, maxAltFt, hoursBefore);
    }

    @RequestMapping(value = "/latLonLimited", method = GET, consumes = "application/json", produces = "application/json")
    public List<AIRSIGMET> getAirsimetsByLatLon(
            @RequestParam(value = HRS_BEFORE, required = true, defaultValue = "1") double hrsBeforeNow,
            @RequestParam(value = MIN_LAT, required = true, defaultValue = "25") int minLat,
            @RequestParam(value = MAX_LAT, required = true, defaultValue = "65") int maxLat,
            @RequestParam(value = MIN_LON, required = true, defaultValue = "-130") int minLon,
            @RequestParam(value = MAX_LON, required = true, defaultValue = "-40") int maxLon) throws Exception{
        return svc.getAirsigmetsInLatLongRectangle(minLat, minLon, maxLat, maxLon, hrsBeforeNow);
    }

}
