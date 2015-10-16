package com.grndctl.controllers;

import com.grndctl.model.taf.Response;
import com.grndctl.model.taf.TAF;
import com.grndctl.model.taf.TimeType;
import com.grndctl.services.TafSvc;
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
@RequestMapping("/taf")
public class TafController {

    private static final Logger LOG = LogManager.getLogger(TafController.class);

    private static final String STATION = "station";

    private static final String TIME_TYPE = "timetype";

    private static final String HRS_BEFORE = "hrsbefore";

    private final TafSvc svc;

    @Autowired
    public TafController(final TafSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, consumes = "application/json", produces = "application/json")
    public List<TAF> getTafs(
            @RequestParam(value = STATION, defaultValue = "KIAD") String code,
            @RequestParam(value = HRS_BEFORE, defaultValue = "2") Double hoursBefore,
            @RequestParam(value = TIME_TYPE, required = false) TimeType timeType) throws Exception {
            return svc.getTafs(code, hoursBefore, timeType.valueOf());
    }

}
