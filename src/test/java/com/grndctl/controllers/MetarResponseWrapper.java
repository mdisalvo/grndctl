package com.grndctl.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grndctl.model.metar.METAR;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "metars" })
@XmlRootElement(name = "metars")
public class MetarResponseWrapper {

    @XmlElement(name = "metars")
    private List<METAR> metars;

    public MetarResponseWrapper() {
    }

    public MetarResponseWrapper(List<METAR> metars) {
        this.metars = metars;
    }

    public List<METAR> getMetars() {
        return metars;
    }

    public void setMetars(List<METAR> metars) {
        this.metars = metars;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetarResponseWrapper that = (MetarResponseWrapper) o;

        return metars != null ? metars.equals(that.metars) : that.metars == null;

    }

    @Override
    public int hashCode() {
        return metars != null ? metars.hashCode() : 0;
    }
}
