package com.grndctl.services;

import com.grndctl.model.metar.METAR;
import com.grndctl.model.metar.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
public class MetarSvc {

    private static final Logger LOG = LogManager.getLogger(MetarSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/dataserver_current/httpparam?" +
            "dataSource=metars&requestType=retrieve&format=xml";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    private static final String STATION_STRING = "&stationString=";

    private static final String MOST_RECENT_CONSTRAINT = "&mostRecentForEachStation=constraint";

    private static final double ONE_HR = 1.0;

    private static Response unmarshall(final InputStream is) throws Exception {
        Unmarshaller<Response> unmarshaller = new Unmarshaller<>(Response.class);
        try {
            return unmarshaller.unmarshall(is);
        } catch (JAXBException | IOException e) {
            throw new Exception(e);
        }
    }

    public List<METAR> getCurrentMetar(String station) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(MOST_RECENT_CONSTRAINT)
                .append(STATION_STRING)
                .append(station)
                .append(HRS_BEFORE)
                .append(ONE_HR)
                .toString());
        LOG.info(url.toString());

        return unmarshall(url.openConnection().getInputStream()).getData().getMETAR();
    }

    public List<METAR> getMetars(String station, double hrsBefore) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(HRS_BEFORE)
                .append(hrsBefore)
                .append(STATION_STRING)
                .append(station)
                .toString());
        LOG.info(url.toString());

        return unmarshall(url.openConnection().getInputStream()).getData().getMETAR();
    }

}
