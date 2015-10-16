package com.grndctl.controllers;

import com.grndctl.model.taf.TAF;
import com.grndctl.model.taf.TimeType;
import com.grndctl.services.TafSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Terminal Aerodrome Forecasts as issued by the NWS.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/taf")
public class TafController {

    private static final String STATION = "station";

    private static final String TIME_TYPE = "timetype";

    private static final String HRS_BEFORE = "hrsbefore";

    private final TafSvc svc;

    @Autowired
    public TafController(final TafSvc svc) {
        this.svc = svc;
    }

    /**
     * Retrieve <code>TAF</code>s hours before now for a station by either issue time or validity.
     *
     * @param code Station string
     * @param hoursBefore Hours before now
     * @param timeType <code>ISSUE</code> or <code>VALID</code>
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<TAF> getTafs(
            @RequestParam(value = STATION, defaultValue = "KIAD") String code,
            @RequestParam(value = HRS_BEFORE, defaultValue = "2") Double hoursBefore,
            @RequestParam(value = TIME_TYPE, required = false) TimeType timeType) throws Exception {
            return svc.getTafs(code, hoursBefore, timeType.valueOf());
    }

}