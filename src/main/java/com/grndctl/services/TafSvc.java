package com.grndctl.services;

import com.grndctl.model.taf.Response;
import com.grndctl.model.taf.TAF;
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
public class TafSvc {

    private static final String RQST_URL = "https://aviationweather.gov/adds/"
            + "dataserver_current/httpparam?datasource=tafs&requesttype=retrieve"
            + "&format=xml";

    private static final String MOST_RECENT_CONSTRAINT = "&mostRecentForEachStation=constraint";

    private static final String STATION_STRING = "&stationString=";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    private static final String TIME_TYPE = "&timeType=";

    private static Response unmarshall(final InputStream is) throws Exception {
        Unmarshaller<Response> unmarshaller = new Unmarshaller<>(Response.class);
        try {
            return unmarshaller.unmarshall(is);
        } catch (JAXBException | IOException e) {
            throw new Exception(e);
        }
    }

    public List<TAF> getCurrentTaf(String station) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(MOST_RECENT_CONSTRAINT)
                .append(STATION_STRING)
                .append(station)
                .append(HRS_BEFORE)
                .append(1)
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getTAF();
    }

    public List<TAF> getTafs(String station, double hrsBefore, String timeType) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(STATION_STRING)
                .append(station)
                .append(HRS_BEFORE)
                .append(hrsBefore)
                .append(TIME_TYPE)
                .append(timeType)
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getTAF();
    }

}
