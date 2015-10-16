package com.grndctl.model.aggregates;


import com.grndctl.model.metar.METAR;
import com.grndctl.model.taf.TAF;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * An object used to provide aggregate meteorological conditions for a field.
 * </p>
 *
 * @author Michael Di Salvo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"metars", "tafs"})
@XmlRootElement(name = "CombinedWx")
public class CombinedWx {

    @XmlElement(name = "metars")
    protected List<METAR> metars = new ArrayList<>(10);
    @XmlElement(name = "tafs")
    protected List<TAF> tafs = new ArrayList<>(10);

    public List<METAR> getMetars() {
        return metars;
    }

    public void setMetars(List<METAR> metars) {
        this.metars = metars;
    }

    public List<TAF> getTafs() {
        return tafs;
    }

    public void setTafs(List<TAF> tafs) {
        this.tafs = tafs;
    }

}
