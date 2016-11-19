package com.grndctl.controllers;

import com.grndctl.ControllerTestSupport;
import com.grndctl.model.metar.METAR;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 */
public class MetarControllerTest extends ControllerTestSupport {

    private static final String TEST_NAME = MetarControllerTest.class.getSimpleName();

    @BeforeClass
    public static void setUp() {
        LOG.info(format(BEG_MSG, TEST_NAME));
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void testGetMetar() throws Exception {
        URL metarUrl = addUrlSpecs("metar", ICAO_CODE);
        metarUrl = new URL(metarUrl + "?hrsBefore=3.0");

        @SuppressWarnings({"unchecked"})
        List<Map<String, Object>> response = rest().getForObject(
                metarUrl.toExternalForm(), List.class

        );

        assert(response.size() > 1);
        response.forEach(metar -> {
            METAR m = (om().convertValue(metar, METAR.class));
            assert(m != null);
            assert(m.getRawText().contains(ICAO_CODE));
            assert(m.getStationId().equals(ICAO_CODE));
        });
    }

}
