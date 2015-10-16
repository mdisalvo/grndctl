package com.grndctl.services;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by michael on 10/16/15.
 */
public class Unmarshaller<T> {

    private T resp;
    private JAXBContext context;
    private javax.xml.bind.Unmarshaller u;

    public Unmarshaller(final Class<T> clazz) throws InstantiationException, IllegalAccessException, JAXBException {
        resp = clazz.newInstance();
        context = JAXBContext.newInstance(resp.getClass());
        u = context.createUnmarshaller();
    }

    @SuppressWarnings({"unchecked"})
    public T unmarshall(final InputStream is) throws JAXBException, IOException {
        resp = (T) u.unmarshal(is);
        is.close();
        return resp;
    }

}
