package com.grndctl.controllers;

import com.grndctl.model.flightplan.ValidationResults;
import com.grndctl.services.IntlFPValidationSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Basic service to validate an ICAO international flight plan String.
 *
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/intlfpvalidator")
public class IntlFPValidationController {

    private final IntlFPValidationSvc svc;

    @Autowired
    public IntlFPValidationController(final IntlFPValidationSvc svc) {
        this.svc = svc;
    }

    /**
     * Accepts an ICAO flight plan as a <code>String</code> to validate.
     *
     * <pre>
     *
     * (FPL-DLH560_IS
     * -1A319/M-SHWY/S
     * -EGLL0600
     * -N0420F370 BPK UQ295 CLN UL620 ARTOV UP44 SOMVA UP155 OKOKO UZ303 DHE UP729 DOSUR P729 LUGAS
     * -EKCH0715 ESMS)
     *
     * </pre>
     *
     * @param flightPlan ICAO flight plan to validate (Default ^)
     * @return <code>ValidationResults</code> object that holds the original flight plan, and the returned messages.
     * @throws Exception
     */
    @RequestMapping(value = "/validate", method = POST, consumes = "application/json", produces = "application/json")
    public ValidationResults validateFlightPlan(
            @RequestBody(required = true) String flightPlan) throws Exception {
        return svc.validateFlightPlan(flightPlan);
    }

}
