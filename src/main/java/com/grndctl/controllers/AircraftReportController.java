package com.grndctl.controllers;

import com.grndctl.model.aircraftrep.AircraftReport;
import com.grndctl.services.AircraftReportSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller to retrieve {@link com.grndctl.model.aircraftrep.AircraftReport}s issued by the NWS.
 *
 * This replaces the {@link com.grndctl.controllers.PirepController}, a service that has been deprecated by the NWS.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping(value = "/aircraftrep")
public class AircraftReportController  {

    private static final String HRS_BEFORE = "hrsbefore";

    private final AircraftReportSvc svc;

    @Autowired
    public AircraftReportController(final AircraftReportSvc svc) {
        this.svc = svc;
    }

    /**
     * Get the reports.  The <code>AircraftReport</code>s.
     *
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @return <code>List</code> of filtered <code>PIREP</code>s
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<AircraftReport> getAircraftReports(
            @RequestParam(value = HRS_BEFORE, defaultValue = "1.0") double hrsBefore) throws Exception {
        return svc.getAircraftReports(hrsBefore);
    }
}
