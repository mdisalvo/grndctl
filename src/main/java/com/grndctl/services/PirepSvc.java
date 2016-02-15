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
@Deprecated
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

    @Deprecated
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