package com.grndctl.services;

import com.grndctl.model.flightplan.ValidationResults;
import com.grndctl.model.metar.METAR;
import com.grndctl.model.station.Station;
import com.grndctl.model.taf.TAF;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Basic service tests.  Not all will be represented as calls can return empty based on current conditions and reports
 * issued by the NWS. This test will be using live data.
 *
 * @author Michael Di Salvo
 */
public class BasicServiceTest {

    private static MetarSvc metarSvc;
    private static TafSvc tafSvc;
    private static StationSvc stationSvc;
    private static IntlFPValidationSvc intlFPValidationSvc;

    @BeforeClass
    public static void beforeClass() {
        metarSvc = new MetarSvc();
        tafSvc = new TafSvc();
        stationSvc = new StationSvc();
        intlFPValidationSvc = new IntlFPValidationSvc();
    }

    @Test
    public void testMetarSvc() throws Exception {
        METAR currentMETAR = metarSvc.getCurrentMetar("KIAD").get(0);
        assertNotNull(currentMETAR);
        assertEquals("KIAD", currentMETAR.getStationId());
    }

    @Test
    public void testTafSvc() throws Exception {
        TAF currentTAF = tafSvc.getCurrentTaf("KIAD").get(0);
        assertNotNull(currentTAF);
        assertEquals("KIAD", currentTAF.getStationId());
    }

    @Test
    public void testStationSvc() throws Exception {
        Station station = stationSvc.getStationInfo("KIAD").get(0);
        assertNotNull(station);
        assertEquals("KIAD", station.getStationId());
    }

    @Test
    public void testFPSvc() throws Exception {
        String fpString = "(FPL-DLH560_IS\n" +
                "-1A319/M-SHWY/S\n" +
                "-EGLL0600\n" +
                "-N0420F370 BPK UQ295 CLN UL620 ARTOV UP44 SOMVA UP155 OKOKO UZ303 DHE UP729 DOSUR P729 LUGAS\n" +
                "-EKCH0715 ESMS)";

        ValidationResults results = intlFPValidationSvc.validateFlightPlan(fpString);
        assertEquals(fpString, results.getFlightPlan());
        assertNotNull(results.getMessages());
    }
}
