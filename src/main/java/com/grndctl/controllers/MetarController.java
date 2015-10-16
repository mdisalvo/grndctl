package com.grndctl.controllers;

import com.grndctl.model.metar.METAR;
import com.grndctl.model.metar.Response;
import com.grndctl.services.MetarSvc;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * Created by michael on 10/16/15.
 */
@RestController
@RequestMapping("/metar")
public class MetarController {

    private static final Logger LOG = LogManager.getLogger(MetarController.class);

    private static final String STATION = "station";

    private static final String HRS_BEFORE = "hrsbefore";

    private final MetarSvc svc;

    @Autowired
    public MetarController(final MetarSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, consumes = "application/json", produces = "application/json")
    public List<METAR> getMetar(
            @RequestParam(value = STATION, defaultValue = "KIAD") String station,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "1") Double hrsBefore) throws Exception {
            if (hrsBefore == null)
                return svc.getCurrentMetar(station);
            else
                return svc.getMetars(station, hrsBefore);
    }

}
