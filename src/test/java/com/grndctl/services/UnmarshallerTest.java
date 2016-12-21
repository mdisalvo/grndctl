/**
 * MIT License
 *
 * Copyright (c) 2016 grndctl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**
 * This file is part of grndctl.
 *
 * grndctl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * grndctl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with grndctl.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.grndctl.services;

import com.grndctl.model.metar.METAR;
import com.grndctl.model.metar.QualityControlFlags;
import com.grndctl.model.metar.SkyCondition;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.junit.Assert;

/**
 * Validate that the unmarshaller correctly creates and translates objects.
 * @author Michael Di Salvo
 */
public class UnmarshallerTest {

    private METAR expected;
    
    private Marshaller m;
    
    private AbstractSvc svc;

    @Before
    public void setup() throws Exception {
        svc = new AbstractSvc<METAR>(METAR.class, "MetarTestSvc") {};
        m = JAXBContext.newInstance(METAR.class).createMarshaller();
        buildExpectedMetar();
    }

    @Test
    public void testUnmarshaller() throws Exception {
        StringWriter sw = new StringWriter();
        m.marshal(expected, sw);
        METAR actual = (METAR)svc.unmarshall(new ByteArrayInputStream(sw.toString().getBytes()));
        Assert.assertEquals(expected, actual);
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
