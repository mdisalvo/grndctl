/**
 * MIT License
 *
 * Copyright (c) 2017 grndctl
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

    private static final String FAA_NOTAM_SEARCH_URL =
            "https://pilotweb.nas.faa.gov/PilotWeb/notamRetrievalByICAOAction.do?" +
            "method=displayByICAOs&actionType=notamRetrievalByICAOs";
    private static final String FORMAT_TYPE = "&formatType=";
    private static final String REPORT_TYPE = "&reportType=";
    private static final String ICAO_CODE = "&retrieveLocId=";
    private static final String USER_AGENT = "grndctl";
    private static final String NOTAM_RIGHT = "notamRight";

    public NotamSvc() { }

    public List<String> getNotamsForCodes(List<String> icaoCodes,
                                          Notam.ReportType reportType,
                                          Notam.FormatType formatType) throws ServiceException {
        String icaoString = StringUtils.join(icaoCodes, ",");

        Document dom;
        try {
            dom = Jsoup
                    .connect(
                        FAA_NOTAM_SEARCH_URL +
                                ICAO_CODE + icaoString +
                                REPORT_TYPE + reportType +
                                FORMAT_TYPE + formatType
                    )
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
