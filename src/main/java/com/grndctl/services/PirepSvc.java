package com.grndctl.services;

import com.grndctl.model.pirep.PIREP;
import com.grndctl.model.pirep.Response;
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
public class PirepSvc {

    private static final Logger LOG = LogManager.getLogger(PirepSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/" +
            "dataserver_current/httpparam?datasource=pireps&" +
            "requesttype=retrieve&format=xml";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    private List<PIREP> pireps;

    private static Response unmarshall(final InputStream is) throws Exception {
        Unmarshaller<Response> unmarshaller = new Unmarshaller<>(Response.class);
        try {
            return unmarshaller.unmarshall(is);
        } catch (JAXBException | IOException e) {
            throw new Exception(e);
        }
    }

    public List<PIREP> getPireps(final double hoursBefore) throws Exception {
        URL url = new URL(new StringBuilder()
                    .append(RQST_URL)
                    .append(HRS_BEFORE)
                    .append(hoursBefore)
                    .toString());
        LOG.info(url.toString());

        return unmarshall(url.openConnection().getInputStream()).getData().getPIREP();
    }

}