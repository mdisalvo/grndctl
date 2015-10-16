package com.grndctl.controllers;

import com.grndctl.model.flightplan.ValidationResults;
import com.grndctl.services.IntlFPValidationSvc;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <pre>
 * Ex.
 *
 * (FPL-DLH560_IS
 * -1A319/M-SHWY/S
 * -EGLL0600
 * -N0420F370 BPK UQ295 CLN UL620 ARTOV UP44 SOMVA UP155 OKOKO UZ303 DHE UP729 DOSUR P729 LUGAS
 * -EKCH0715 ESMS)
 *
 * </pre>
 *
 * Created by michael on 10/16/15.
 */
@RestController
@RequestMapping("/intlfpvalidator")
public class IntlFPValidationController {

    private static final Logger LOG = LogManager.getLogger(IntlFPValidationController.class);

    private final IntlFPValidationSvc svc;

    @Autowired
    public IntlFPValidationController(final IntlFPValidationSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "/validate", method = POST)
    public ValidationResults validateFlightPlan(
            @RequestBody(required = true) String flightPlan) throws Exception {
        return svc.validateFlightPlan(flightPlan);
    }

}
