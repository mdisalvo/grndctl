package com.grndctl.services;

import com.grndctl.model.metar.METAR;
import com.grndctl.model.metar.QualityControlFlags;
import com.grndctl.model.metar.SkyCondition;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Validate that the unmarshaller correctly creates and translates objects.
 *
 * @author Michael Di Salvo
 */
public class UnmarshallerTest {

    private Unmarshaller<com.grndctl.model.metar.Response> unmarshaller;
    private METAR expected;

    @Before
    public void setup() throws Exception {
        unmarshaller = new Unmarshaller<>(com.grndctl.model.metar.Response.class);
        buildExpectedMetar();
    }

    @Test
    public void testUnmarshaller() throws Exception {
        METAR actual = unmarshaller.unmarshall(UnmarshallerTest.class.getResourceAsStream("/KIAD.xml")).getData().getMETAR().get(0);
        assertEquals(expected, actual);
    }

    private METAR buildExpectedMetar() {
        expected = new METAR();
        expected.setRawText("KIAD 181952Z 32009G19KT 10SM SCT065 BKN090 11/M05 A3033 RMK AO2 SLP273 VIRGA T01061050");
        expected.setStationId("KIAD");
        expected.setObservationTime("2015-10-18T19:52:00Z");
        expected.setLatitude(new Float(38.93));
        expected.setLongitude(new Float(-77.45));
        expected.setTempC(new Float(10.6));
        expected.setDewpointC(new Float(-5.0));
        expected.setWindDirDegrees(320);
        expected.setWindSpeedKt(9);
        expected.setWindGustKt(19);
        expected.setVisibilityStatuteMi(new Float(10.0));
        expected.setAltimInHg(new Float(30.33071));
        expected.setSeaLevelPressureMb(new Float(1027.3));

        QualityControlFlags qcf = new QualityControlFlags();
        qcf.setAutoStation("TRUE");

        expected.setQualityControlFlags(qcf);

        List<SkyCondition> sc = new ArrayList<>();
        SkyCondition c = new SkyCondition();
        c.setSkyCover("SCT");
        c.setCloudBaseFtAgl(6500);
        sc.add(c);
        c = new SkyCondition();
        c.setSkyCover("BKN");
        c.setCloudBaseFtAgl(9000);
        sc.add(c);

        expected.setSkyCondition(sc);
        expected.setFlightCategory("VFR");
        expected.setMetarType("METAR");
        expected.setElevationM(new Float(93.0));

        return expected;
    }

}
