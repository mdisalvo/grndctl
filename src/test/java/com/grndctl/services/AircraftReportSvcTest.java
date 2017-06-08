/**
 * MIT License
 *
 * Copyright (c) 2017 grndctl
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
package com.grndctl.services;

import com.grndctl.SvcTestSupport;
import com.grndctl.model.aircraftrep.AircraftReport;
import com.grndctl.model.aircraftrep.ReportType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static java.lang.String.format;

/**
 */
public class AircraftReportSvcTest extends SvcTestSupport {

    private static final String TEST_NAME = AircraftReportSvcTest.class.getName();

    public AircraftReportSvcTest() {
        super();
    }

    @BeforeClass
    public static void setup() {
        LOG.info(format(BEG_MSG, TEST_NAME));
    }

    @AfterClass
    public static void tearDown() {
        LOG.info(format(END_MSG, TEST_NAME));
    }

    @Test
    public void getAircraftReports() throws Exception {

        List<AircraftReport> reports = aircraftReportSvc.getAircraftReports(10.0, ReportType.AIREP);
        reports.forEach(aircraftReport -> {
            assert aircraftReport != null;
            assert aircraftReport.getReportType().equals(ReportType.AIREP.toString());
        });

        reports = aircraftReportSvc.getAircraftReports(10.0, ReportType.PIREP);
        reports.forEach(aircraftReport -> {
            assert aircraftReport != null;
            assert aircraftReport.getReportType().equals(ReportType.PIREP.toString());
        });

        reports = aircraftReportSvc.getAircraftReports(10.0, ReportType.URGENT_PIREP);
        reports.forEach(aircraftReport -> {
            assert aircraftReport != null;
            assert aircraftReport.getReportType().equals(ReportType.URGENT_PIREP.toString());
        });

    }

}
