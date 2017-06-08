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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;

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
    public final T unmarshall(final InputStream is) throws ServiceException {
        try {
            T resp = responseType.newInstance();
            JAXBContext context = JAXBContext.newInstance(resp.getClass());
            resp = (T) context.createUnmarshaller().unmarshal(is);
            is.close();
            return resp;
        } catch (IOException | JAXBException | InstantiationException | IllegalAccessException e) {
            throw new ServiceException(e);
        }
    }
    
    public String getName() {
        return this.svcName;
    }

}
