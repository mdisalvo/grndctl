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
package com.grndctl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.station.FaaStation;
import com.grndctl.model.station.Response;
import com.grndctl.model.station.Station;
import com.grndctl.model.station.StationCodeType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * 
 * @author Michael Di Salvo
 */
@Service
public class StationSvc extends AbstractSvc<com.grndctl.model.station.Response> {
    
    private static final String NAME = StationSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(StationSvc.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String ADDS_RQST_URL = "https://aviationweather.gov/adds/"
            + "dataserver_current/httpparam?datasource=stations&requesttype=retrieve"
            + "&format=xml";

    private static final String FAA_RQST_URL = "http://services.faa.gov/airport/status/";

    private static final String RQST_FORMAT_JSON = "?format=application/json";

    private static final String STATION_STRING = "&stationString=";

    public StationSvc() {
        super(Response.class, NAME);
    }
    
    public List<Station> getStationInfo(String code) throws ServiceException {
        try {
            URL url = new URL(ADDS_RQST_URL + STATION_STRING + code.toUpperCase());
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getStation();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public FaaStation getFAAStationStatus(String iataCode) throws ServiceException {
        try {
            URL url = new URL(FAA_RQST_URL + iataCode + RQST_FORMAT_JSON);
            LOG.info(url.toString());

            try (InputStreamReader isr = new InputStreamReader(url.openStream())) {
                return OBJECT_MAPPER.readValue(
                        CharStreams.toString(isr).getBytes(), FaaStation.class
                );
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean stationExists(String code, StationCodeType codeType) throws ServiceException {
        if (codeType.equals(StationCodeType.ICAO)) {
            return (!getStationInfo(code).isEmpty());
        }

        if (codeType.equals(StationCodeType.IATA)) {
            try {
                getFAAStationStatus(code);
            } catch (ServiceException e) {
                return false;
            }
            return true;
        }

        return false;
    }

}
