package com.grndctl.controllers;

import com.grndctl.model.metar.METAR;
import com.grndctl.services.MetarSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * {@link METAR}s for a station.
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/metar")
public class MetarController {

    private static final String STATION = "station";

    private static final String HRS_BEFORE = "hrsbefore";

    private final MetarSvc svc;

    @Autowired
    public MetarController(final MetarSvc svc) {
        this.svc = svc;
    }

    /**
     * <code>METAR</code>s for a station.  <code>hrsBefore</code> parameter is provided to retrieve historical reports.
     *
     * @param station Station string (Default -> KIAD)
     * @param hrsBefore Hours before now (Default -> 1.0)
     * @return <code>List</code> of filtered <code>METAR</code>s
     * @throws Exception
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public List<METAR> getMetar(
            @RequestParam(value = STATION, defaultValue = "KIAD") String station,
            @RequestParam(value = HRS_BEFORE, required = false, defaultValue = "1.0") Double hrsBefore) throws Exception {
            if (hrsBefore == null)
                return svc.getCurrentMetar(station);
            else
                return svc.getMetars(station, hrsBefore);
    }

}
