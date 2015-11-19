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

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Michael Di Salvo
 * @param <T> Service response type
 */
public abstract class AbstractSvc<T> {
    
    private final Class<T> responseType;
    
    private final String svcName;
    
    public AbstractSvc(final Class<T> clazz, final String svcName) {
        this.responseType = clazz;
        this.svcName = svcName;
    }
    
    /**
     * 
     * 
     * @param is
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({"unchecked"})
    public final T unmarshall(final InputStream is) throws Exception {
        try {
            T resp = responseType.newInstance();
            JAXBContext context = JAXBContext.newInstance(resp.getClass());
            resp = (T) context.createUnmarshaller().unmarshal(is);
            is.close();
            return resp;
        } catch (IOException | JAXBException | InstantiationException | IllegalAccessException e) {
            throw new Exception(e);
        }
    }
    
    public String getName() {
        return this.svcName;
    }
}
