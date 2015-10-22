package com.grndctl.services;

import com.grndctl.model.aircraftrep.AircraftReport;
import com.grndctl.model.aircraftrep.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by michael on 10/21/15.
 */
@Service
public class AircraftReportSvc {

    private static final Logger LOG = LogManager.getLogger(AircraftReportSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/" +
            "dataserver_current/httpparam?datasource=aircraftreports&" +
            "requesttype=retrieve&format=xml";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    private static Response unmarshall(final InputStream is ) throws Exception {
        Unmarshaller<Response> unmarshaller = new Unmarshaller<>(Response.class);
        try {
            return unmarshaller.unmarshall(is);
        } catch (JAXBException | IOException e) {
            throw new Exception(e);
        }
    }

    public List<AircraftReport> getAircraftReports(final double hrsBefore) throws Exception {
        URL url = new URL(new StringBuilder()
                    .append(RQST_URL)
                    .append(HRS_BEFORE)
                    .append(hrsBefore)
                    .toString());
        LOG.info(url.toString());

        return unmarshall(url.openStream()).getData().getAircraftReport();
    }

}
