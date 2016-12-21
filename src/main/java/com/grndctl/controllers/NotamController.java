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
package com.grndctl.controllers;

import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.flightplan.Notam;
import com.grndctl.services.NotamSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/notam")
public class NotamController {

    private static final String CODES = "codes";

    private static final String REPORT_TYPE = "reportType";

    private static final String FORMAT_TYPE = "formatType";

    private final NotamSvc svc;

    @Autowired
    public NotamController(final NotamSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getNotamsForCodes",
            nickname = "getNotamsForCodes",
            response = String.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<String>> getNotamsForCodes(
            @RequestParam(value = CODES) List<String> codes,
            @RequestParam(value = REPORT_TYPE, required = false) Notam.ReportType reportType,
            @RequestParam(value = FORMAT_TYPE, required = false) Notam.FormatType formatType) throws ServiceException {

        if (reportType == null) {
            reportType = Notam.ReportType.RAW;
        }

        if (formatType == null) {
            formatType = Notam.FormatType.DOMESTIC;
        }

        return ResponseEntity.ok(svc.getNotamsForCodes(codes, reportType, formatType));
    }

}
