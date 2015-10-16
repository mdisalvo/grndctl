package com.grndctl.controllers;

import com.grndctl.model.station.Response;
import com.grndctl.model.station.Station;
import com.grndctl.services.StationSvc;
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
@RequestMapping("/station")
public class StationController {

    private static final Logger LOG = LogManager.getLogger(StationController.class);

    private static final String STATION = "station";

    private final StationSvc svc;

    @Autowired
    public StationController(final StationSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET)
    public List<Station> getStationInfo(
            @RequestParam(value = STATION, defaultValue = "KIAD") final String station) throws Exception {
            return svc.getStationInfo(station);
    }

}
