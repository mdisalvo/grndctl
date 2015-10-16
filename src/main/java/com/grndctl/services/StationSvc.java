package com.grndctl.services;

import com.grndctl.model.station.Response;
import com.grndctl.model.station.Station;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by michael on 10/16/15.
 */
@Service
public class StationSvc {

    private static final String RQST_URL = "https://aviationweather.gov/adds/"
            + "dataserver_current/httpparam?datasource=stations&requesttype=retrieve"
            + "&format=xml";

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
                .append(RQST_URL)
                .append(STATION_STRING)
                .append(code.toUpperCase())
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getStation();
    }

}
