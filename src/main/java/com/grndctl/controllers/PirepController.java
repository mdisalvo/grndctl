package com.grndctl.controllers;

import com.grndctl.model.pirep.PIREP;
import com.grndctl.services.PirepSvc;
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
@RequestMapping(value = "/pirep")
public class PirepController {

    private static final Logger LOG = LogManager.getLogger(PirepController.class);

    private static final String HRS_BEFORE = "hrsbefore";

    private final PirepSvc svc;

    @Autowired
    public PirepController(final PirepSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET)
    public List<PIREP> getPireps(
            @RequestParam(value = HRS_BEFORE, defaultValue = "1") Double hrsBefore) throws Exception {
        return svc.getPireps(hrsBefore);
    }
}
