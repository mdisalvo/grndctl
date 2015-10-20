package com.grndctl.model.aggregates;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Calculates wind components on creation to store internally.
 * </p>
 *
 * @author Michael Di Salvo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WindComponent")
public class WindComponent {

    @XmlElement(name = "headWind")
    private double headWind;
    @XmlElement(name = "crossWind")
    private double crossWind;

    public WindComponent(final double windSpeed, final double windDirection, final double heading) {
        calculateComponents(((windDirection * Math.PI) / 180), ((heading * Math.PI) / 180), windSpeed);
    }

    private void calculateComponents(double windDirection, double heading, double windSpeed) {
        headWind = Math.cos(windDirection - heading) * windSpeed;
        crossWind = Math.sin(windDirection - heading) * windSpeed;
    }

    public double getHeadWind() {
        return headWind;
    }

    public double getCrossWind() {
        return crossWind;
    }
}
