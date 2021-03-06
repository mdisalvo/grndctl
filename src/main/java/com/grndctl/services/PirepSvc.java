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
import com.grndctl.model.pirep.PIREP;
import com.grndctl.model.pirep.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Michael Di Salvo
 */
@Service
public class PirepSvc extends AbstractSvc<com.grndctl.model.pirep.Response> {
    
    private static final String NAME = PirepSvc.class.getSimpleName();

    private static final Logger LOG = LogManager.getLogger(PirepSvc.class);

    private static final String RQST_URL = "https://aviationweather.gov/adds/" +
            "dataserver_current/httpparam?datasource=pireps&" +
            "requesttype=retrieve&format=xml";

    private static final String HRS_BEFORE = "&hoursBeforeNow=";

    public PirepSvc() {
        super(Response.class, NAME);
    }

    public List<PIREP> getPireps(final double hrsBefore) throws ServiceException {
        try {
            URL url = new URL(RQST_URL + HRS_BEFORE + hrsBefore);
            LOG.info(url.toString());

            return unmarshall(url.openStream()).getData().getPIREP();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}