package com.grndctl.controllers;

import com.grndctl.model.station.Station;
import com.grndctl.services.StationSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Station information as provided by the NWS.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/station")
public class StationController {

    private static final String STATION = "station";

    private final StationSvc svc;

    @Autowired
    public StationController(final StationSvc svc) {
        this.svc = svc;
    }

    /**
     * Get information for a field such as latest <code>METAR</code> latest <code>TAF</code>, forecasts, etc.
     *
     * @param station Station string
     * @return <code>List</code> of filtered <code>Station</code>s
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<Station> getStationInfo(
            @RequestParam(value = STATION, defaultValue = "KIAD") final String station) throws Exception {
        return svc.getStationInfo(station);
    }

}
