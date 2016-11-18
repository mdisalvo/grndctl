package com.grndctl.services;

import com.google.common.io.CharStreams;
import com.grndctl.exceptions.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Michael Di Salvo
 */
@Service
public class ChartsSvc {

    private static final Logger LOG = LogManager.getLogger(ChartsSvc.class);

    private static final String AIRCHARTS_URL = "http://api.aircharts.org/Airport/%s.json";

    private static final String THANKS_VALUE = "All information retrieved from AirCharts at http://www.aircharts.org";

    private static final String THANKS_KEY = "thanks";

    public ChartsSvc() { }

    public String getStationCharts(final String icaoCode) throws ServiceException {
        URL url;
        try {
            url = new URL(String.format(AIRCHARTS_URL, icaoCode.toUpperCase()));
            LOG.info(url.toString());
            try (InputStreamReader isr = new InputStreamReader(url.openStream())){
                return (new JSONObject(CharStreams.toString(isr)).put(THANKS_KEY, THANKS_VALUE)).toString();
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}
