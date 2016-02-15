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
package com.grndctl.services;

import com.grndctl.exceptions.ServiceException;
import com.grndctl.model.flightplan.Notam;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Di Salvo
 */
@Service
public class NotamSvc {

    private static final String FAA_NOTAM_SEARCH_URL = "https://pilotweb.nas.faa.gov/PilotWeb/notamRetrievalByICAOAction.do?" +
            "method=displayByICAOs&actionType=notamRetrievalByICAOs";
    private static final String FORMAT_TYPE = "&formatType=";
    private static final String REPORT_TYPE = "&reportType=";
    private static final String ICAO_CODE = "&retrieveLocId=";
    private static final String USER_AGENT = "grndctl";
    private static final String NOTAM_RIGHT = "notamRight";

    public NotamSvc() { }

    public List<String> getNotamsForCodes(List<String> icaoCodes, Notam.ReportType reportType, Notam.FormatType formatType) throws ServiceException {
        String icaoString = StringUtils.join(icaoCodes, ",");

        Document dom;
        try {
            dom = Jsoup
                    .connect(FAA_NOTAM_SEARCH_URL + ICAO_CODE + icaoString + REPORT_TYPE + reportType + FORMAT_TYPE + formatType)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            throw new ServiceException(e);
        }

        List<String> notams = new ArrayList<>();
        dom.getElementsByAttributeValue("id", NOTAM_RIGHT).forEach(e -> notams.add(e.text()));

        return notams;
    }

}
