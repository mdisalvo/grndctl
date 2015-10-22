package com.grndctl.services;

import com.google.common.io.CharStreams;
import com.grndctl.model.station.Response;
import com.grndctl.model.station.Station;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by michael on 10/16/15.
 */
@Service
public class StationSvc {

    private static final Logger LOG = LogManager.getLogger(StationSvc.class);

    private static final String ADDS_RQST_URL = "https://aviationweather.gov/adds/"
            + "dataserver_current/httpparam?datasource=stations&requesttype=retrieve"
            + "&format=xml";

    private static final String FAA_RQST_URL = "http://services.faa.gov/airport/status/";

    private static final String RQST_FORMAT_JSON = "?format=application/json";

    private static final String STATION_STRING = "&stationString=";

    private static Response unmarshall(final InputStream is) throws Exception {
        Unmarshaller<Response> unmarshaller = new Unmarshaller<>(Response.class);
        try {
            return unmarshaller.unmarshall(is);
        } catch (JAXBException | IOException e) {
            throw new Exception(e);
        }
    }

    public List<Station> getStationInfo(String code) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(ADDS_RQST_URL)
                .append(STATION_STRING)
                .append(code.toUpperCase())
                .toString());
        LOG.info(url.toString());

        return unmarshall(url.openStream()).getData().getStation();
    }

    public String getFAAStationStatus(String iataCode) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(FAA_RQST_URL)
                .append(iataCode)
                .append(RQST_FORMAT_JSON)
                .toString());
        LOG.info(url.toString());

        return CharStreams.toString(new InputStreamReader(url.openStream()));
    }

}
