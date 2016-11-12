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

import com.google.common.io.CharStreams;
import com.grndctl.exceptions.ResourceNotFoundException;
import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.station.StationCodeType;
import com.grndctl.services.StationSvc;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author Michael Di Salvo
 */
@RestController
@RequestMapping("/charts")
public class ChartsController {

    private static final Logger LOG = LogManager.getLogger(ChartsController.class);

    private static final String ICAO_CODE = "icaocode";

    private static final String AIRCHARTS_URL = "http://api.aircharts.org/Airport/%s.json";

    private static final String THANKS_KEY = "thanks";

    private static final String THANKS_VALUE = "All information retrieved from AirCharts at http://www.aircharts.org";

    private final StationSvc stationSvc;

    @Autowired
    public ChartsController(final StationSvc stationSvc) {
        this.stationSvc = stationSvc;
    }

    /**
     * Get the Aerodrome Charts for a field by ICAO code.  All credit for information goes to AirCharts,
     * Response Entity:
     * <pre>
     *     {
     *         "thanks": "All information retrieved from AirCharts at http://www.aircharts.org",
     *         "KIAD": {
     *              "charts": [
     *                  {
     *                      "name": "AIRPORT DIAGRAM",
     *                      "id": "9f03735f-becb-5abd-822f-a4df05387576",
     *                      "type": "General",
     *                      "url": "http://www.aircharts.org/data/view.php?id=9f03735f-becb-5abd-822f-a4df05387576"
     *                  }
     *              ],
     *              "info": {
     *                  "iata": "IAD",
     *                  "name": "WASHINGTON DULLES INTL".
     *                  "icao": "KIAD
     *              }
     *         }
     *     }
     * </pre>
     *
     * @param icaoCode Station string [REQ'D]
     * @return <code>String</code> that represents the returned model object from AirCharts
     * @throws ServiceException
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{icaocode}", method = GET, produces = "application/json")
    public ResponseEntity<String> getStationsCharts(@PathVariable(value = ICAO_CODE) final String icaoCode)
            throws ServiceException, ResourceNotFoundException {
        if (!stationSvc.stationExists(icaoCode, StationCodeType.ICAO)) {
            throw new ResourceNotFoundException(String.format("Station with ICAO code %s does not exist.", icaoCode));
        }

        URL url;
        try {
            url = new URL(String.format(AIRCHARTS_URL, icaoCode.toUpperCase()));
            LOG.info(url.toString());

            try (InputStreamReader isr = new InputStreamReader(url.openStream())) {
                return ResponseEntity.ok(generateResponseEntity(isr));
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    private static String generateResponseEntity(InputStreamReader isr) throws IOException {
        return (new JSONObject(CharStreams.toString(isr)).put(THANKS_KEY, THANKS_VALUE)).toString();
    }

}
