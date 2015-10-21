package com.grndctl.controllers;

import com.grndctl.model.pirep.PIREP;
import com.grndctl.services.PirepSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller to retrieve {@link PIREP}s issued by the NWS.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping(value = "/pirep")
public class PirepController {

    private static final String HRS_BEFORE = "hrsbefore";

    private final PirepSvc svc;

    @Autowired
    public PirepController(final PirepSvc svc) {
        this.svc = svc;
    }

    /**
     * Get the reps.  The <code>PIREP</code>s.
     *
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @return <code>List</code> of filtered <code>PIREP</code>s
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<PIREP> getPireps(
            @RequestParam(value = HRS_BEFORE, defaultValue = "1.0") Double hrsBefore) throws Exception {
        return svc.getPireps(hrsBefore);
    }
}
