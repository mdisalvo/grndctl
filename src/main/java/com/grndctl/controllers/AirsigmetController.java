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
import com.grndctl.model.airsigmet.AIRSIGMET;
import com.grndctl.services.AirsigmetSvc;
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
@RequestMapping("/airsigmet")
public class AirsigmetController {

    private static final String HRS_BEFORE = "hrsBefore";
    private static final String MAX_ALT_FT = "maxAltitudeFt";
    private static final String MIN_ALT_FT = "minAltitudeFt";
    private static final String MIN_LAT = "minLat";
    private static final String MAX_LAT = "maxLat";
    private static final String MIN_LON = "minLon";
    private static final String MAX_LON = "maxLon";

    private final AirsigmetSvc svc;

    @Autowired
    public AirsigmetController(final AirsigmetSvc svc) {
        this.svc = svc;
    }

    @RequestMapping(value = "", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAirsigmets",
            nickname = "getAirsigmets",
            response = AIRSIGMET.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AIRSIGMET>> getAirsigmets() throws ServiceException {
        return ResponseEntity.ok(svc.getAirsigmets());
    }

    @RequestMapping(value = "/altLimited", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAirsigmetsByAlt",
            nickname = "getAirsigmetsByAlt",
            response = AIRSIGMET.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AIRSIGMET>> getAirsigmetsByAlt(
            @RequestParam(value = HRS_BEFORE) double hrsBefore,
            @RequestParam(value = MIN_ALT_FT) int minAltFt,
            @RequestParam(value = MAX_ALT_FT) int maxAltFt) throws ServiceException {
        return ResponseEntity.ok(svc.getAirsigmetsInAltitudeRange(minAltFt, maxAltFt, hrsBefore));
    }

    @RequestMapping(value = "/latLonLimited", method = GET, produces = "application/json")
    @ApiOperation(
            value = "getAirsigmetsByLatLon",
            nickname = "getAirsigmetsByLatLon",
            response = AIRSIGMET.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<AIRSIGMET>> getAirsimetsByLatLon(
            @RequestParam(value = HRS_BEFORE) double hrsBefore,
            @RequestParam(value = MIN_LAT) int minLat,
            @RequestParam(value = MAX_LAT) int maxLat,
            @RequestParam(value = MIN_LON) int minLon,
            @RequestParam(value = MAX_LON) int maxLon) throws ServiceException {
        return ResponseEntity.ok(svc.getAirsigmetsInLatLongRectangle(minLat, minLon, maxLat, maxLon, hrsBefore));
    }

}
