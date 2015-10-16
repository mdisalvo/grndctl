package com.grndctl.services;

import com.grndctl.model.airsigmet.AIRSIGMET;
import com.grndctl.model.airsigmet.Response;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by michael on 10/16/15.
 */
@Service
public class AirsigmetSvc {

    private static final String RQST_URL = "https://aviationweather.gov/adds/" +
            "dataserver_current/httpparam?datasource=airsigmets&requesttype=retrieve" +
            "&format=xml";

    private static final String MOST_RECENT_CONSTRAINT = "&mostRecent=true";

    private static final String MIN_ALTITUDE_FT = "&minAltitudeFt=";

    private static final String MAX_ALTITUDE_FT = "&maxAltitudeFt=";

    private static final String MIN_LAT = "&minLat=";

    private static final String MAX_LAT = "&maxLat=";

    private static final String MIN_LON = "&minLon=";

    private static final String MAX_LON = "&maxLon=";

    private static final String HOURS_BEFORE_NOW = "&hoursBeforeNow=";

    private static final String START_TIME = "&startTime=";

    private static final String END_TIME = "&endTime=";

    private static final String MIDNIGHT_Z = "T00:00:00Z";

    private static Response unmarshall(final InputStream is) throws Exception {
        Unmarshaller<Response> unmarshaller = new Unmarshaller<>(Response.class);
        try {
            return unmarshaller.unmarshall(is);
        } catch (JAXBException | IOException e) {
            throw new Exception(e);
        }
    }

    public List<AIRSIGMET> getAirsigmets() throws Exception {
        String[] times = startAndEndTimes();
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(MOST_RECENT_CONSTRAINT)
                .append(START_TIME)
                .append(times[0])
                .append(END_TIME)
                .append(times[1])
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getAIRSIGMET();
    }

    public List<AIRSIGMET> getAirsigmetsHoursBeforeNow(double hoursBeforeNow) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(HOURS_BEFORE_NOW)
                .append(hoursBeforeNow)
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getAIRSIGMET();
    }

    public List<AIRSIGMET> getAirsigmetsInAltitudeRange(int minAltFt, int maxAltFt, double hrsBeforeNow)
            throws Exception{
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(MIN_ALTITUDE_FT)
                .append(minAltFt)
                .append(MAX_ALTITUDE_FT)
                .append(maxAltFt)
                .append(HOURS_BEFORE_NOW)
                .append(hrsBeforeNow)
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getAIRSIGMET();
    }

    public List<AIRSIGMET> getAirsigmetsInLatLongRectangle(int minLat, int minLong, int maxLat, int maxLong,
                                                           double hrsBeforeNow) throws Exception {
        URL url = new URL(new StringBuilder()
                .append(RQST_URL)
                .append(MIN_LON)
                .append(minLong)
                .append(MAX_LON)
                .append(maxLong)
                .append(MIN_LAT)
                .append(minLat)
                .append(MAX_LAT)
                .append(maxLat)
                .append(HOURS_BEFORE_NOW)
                .append(hrsBeforeNow)
                .toString());
        return unmarshall(url.openConnection().getInputStream()).getData().getAIRSIGMET();
    }

    private static String[] startAndEndTimes() {
        String[] dates = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        dates[0] = (sdf.format(cal.getTime()) + MIDNIGHT_Z);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        dates[1] = (sdf.format(cal.getTime()) + MIDNIGHT_Z);

        return dates;
    }

}
