package com.grndctl.controllers;

import com.grndctl.model.aggregates.CombinedWx;
import com.grndctl.services.MetarSvc;
import com.grndctl.services.TafSvc;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by michael on 10/16/15.
 */
@RestController
@RequestMapping("/combinedwx")
public class CombinedWxController {

    private static final Logger LOG = LogManager.getLogger(CombinedWxController.class);

    private static final String STATION = "station";

    private static final String HOURS_BEFORE = "hoursbefore";

    private MetarSvc metarSvc;
    private TafSvc tafSvc;

    @Autowired
    public CombinedWxController(final MetarSvc metarSvc, final TafSvc tafSvc) {
        this.metarSvc = metarSvc;
        this.tafSvc = tafSvc;
    }

    @RequestMapping(value = "", method = GET)
    public CombinedWx getCombinedWx(
            @RequestParam(value = STATION, defaultValue = "KIAD") String station,
            @RequestParam(value = HOURS_BEFORE, required = false, defaultValue = "1") Double hoursBefore) throws Exception {

        CombinedWx resp = new CombinedWx();
        resp.setMetars(metarSvc.getMetars(station, (hoursBefore == null || hoursBefore < 1.0 ? 1.0 : hoursBefore)));
        resp.setTafs(tafSvc.getCurrentTaf(station));

        return resp;
    }

}
