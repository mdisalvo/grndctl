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
package com.grndctl.controllers;

import com.grndctl.ServiceException;
import com.grndctl.model.notam.Notam;
import com.grndctl.services.NotamSvc;
import com.qmino.miredot.annotations.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/notam")
public class NotamController extends AbstractController {

    private static final String CODES = "codes";

    private static final String REPORT_TYPE = "reportType";

    private static final String FORMAT_TYPE = "formatType";

    private final NotamSvc svc;

    @Autowired
    public NotamController(final NotamSvc svc) {
        this.svc = svc;
    }

    /**
     * Retrieve <code>NOTAM</code>s for the requested aerodromes/fir's.  Example Response Entity:
     *
     * <pre>
     *     [
     *         "!IAD 11/127 IAD RWY 30 RVRR OUT OF SERVICE 1511210332-151127200EST",
     *         "!IAD 11/126 IAD RWY 12 RVRT OUT OF SERVICE 1511210331-1511272000EST",
     *         "!FDC 5/3437 ZDC ROUTE ZDC ZTL.\nQ58 KELLN, SC TO PEETT, NC NA.\n1510271603-1604201603EST"
     *     ]
     * </pre>
     *
     * @param codes Aerodrome/FIR boundary ICAO codes to retrieve <code>NOTAM</code>s for (Default -> [KIAD, ZDC])
     * @param reportType
     * @param formatType
     * @return <code>List</code> of raw <code>NOTAM</code> Strings for the requested codes
     * @throws ServiceException
     */
    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ReturnType(value = "java.util.List<java.lang.String>")
    public ResponseEntity<List<String>> getNotamsForCodes(
            @RequestParam(value = CODES) List<String> codes,
            @RequestParam(value = REPORT_TYPE) Notam.ReportType reportType,
            @RequestParam(value = FORMAT_TYPE) Notam.FormatType formatType) throws ServiceException {

        if (codes == null || codes.isEmpty()) {
            codes = new ArrayList<String>(){{add("KIAD");add("ZDC");}};
        }

        if (reportType == null) {
            reportType = Notam.ReportType.RAW;
        }

        if (formatType == null) {
            formatType = Notam.FormatType.DOMESTIC;
        }

        return new ResponseEntity<>(svc.getNotamsForCodes(codes, reportType, formatType), HttpStatus.OK);
    }

}
