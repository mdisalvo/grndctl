package com.grndctl.model.aggregates;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *
 * @author Michael Di Salvo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"charts"})
@XmlRootElement
public class Charts {

    @XmlElement
    private List<Chart> charts;

    public Charts(List<Chart> charts) {
        this.charts = charts;
    }

    public Charts() { }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    @Override
    public String toString() {
        return "Charts{" +
                "charts=" + charts +
                '}';
    }
}
