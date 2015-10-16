package com.grndctl.controllers;

import com.grndctl.model.aggregates.CombinedWx;
import com.grndctl.services.MetarSvc;
import com.grndctl.services.TafSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Combines the {@link MetarSvc} with the {@link TafSvc} to get the combined meteorological conditions for an aerodrome.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/combinedwx")
public class CombinedWxController {

    private static final String STATION = "station";

    private static final String HOURS_BEFORE = "hoursbefore";

    private MetarSvc metarSvc;
    private TafSvc tafSvc;

    @Autowired
    public CombinedWxController(final MetarSvc metarSvc, final TafSvc tafSvc) {
        this.metarSvc = metarSvc;
        this.tafSvc = tafSvc;
    }

    /**
     * The combined conditions for a field. Takes a string parameter for the field to retrieve, and hours before now.
     *
     * @param station Station string (Ex & Default -> KIAD)
     * @param hoursBefore Hours before now (Default -> 1.0)
     * @return <code>CombinedWx</code> entity
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public CombinedWx getCombinedWx(
            @RequestParam(value = STATION, defaultValue = "KIAD") String station,
            @RequestParam(value = HOURS_BEFORE, required = false, defaultValue = "1.0") Double hoursBefore) throws Exception {

        CombinedWx resp = new CombinedWx();
        resp.setMetars(metarSvc.getMetars(station, (hoursBefore == null || hoursBefore < 1.0 ? 1.0 : hoursBefore)));
        resp.setTafs(tafSvc.getCurrentTaf(station));

        return resp;
    }

}
