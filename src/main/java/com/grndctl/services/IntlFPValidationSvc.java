/**
 * MIT License
 *
 * Copyright (c) 2016 grndctl
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
import com.grndctl.model.flightplan.ValidationResults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 * FlightPlanValidator implementation that relies on Eurocontrol for flight plan validation.  This will eventually
 * include Eurocontrol error message specific parsing.  For now all messages are returned in a list.
 *
 * IFPS ONLY!
 *
 * <pre>
 * Ex.
 *
 * (FPL-DLH560_IS
 * -1A319/M-SHWY/S
 * -EGLL0600
 * -N0420F370 BPK UQ295 CLN UL620 ARTOV UP44 SOMVA UP155 OKOKO UZ303 DHE UP729 DOSUR P729 LUGAS
 * -EKCH0715 ESMS)
 *
 * </pre>
 *
 * </p>
 *
 * @author Michael Di Salvo
 */
@Service
public class IntlFPValidationSvc {

    private static final String FP_VALIDATION_URL = "http://validation.eurofpl.eu";
    private static final String USER_AGENT = "grndctl";
    private static final String IFPUV_RESULT = "ifpuv_result";

    public IntlFPValidationSvc() { }

    public ValidationResults validateFlightPlan(final String flightPlan) throws ServiceException {
        try {
            return sendRequestAndInterpretResults(flightPlan);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    private ValidationResults sendRequestAndInterpretResults(final String flightPlan) throws IOException {
        Document validationDom = Jsoup
                .connect(FP_VALIDATION_URL)
                .data("freeEntry", flightPlan)
                .data("freeSubmit", "Validate")
                .userAgent(USER_AGENT)
                .timeout(5000)
                .post();

        ValidationResults results = new ValidationResults(flightPlan,
                parseMessagesFromElements(validationDom.getElementsByClass(IFPUV_RESULT)));

        return results;
    }

    private String parseMessagesFromElements(final List<Element> ifpuvResults) {
        checkNotNull(ifpuvResults);
        return ifpuvResults.get(0).text();
    }

}
