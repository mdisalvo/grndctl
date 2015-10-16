package com.grndctl.model.aggregates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

    private double windSpeed;
    private int windDirection;
    private double heading;
    @XmlElement(name = "headWind")
    private double headWind;
    @XmlElement(name = "crossWind")
    private double crossWind;

    public WindComponent(final double windSpeed, final int windDirection, final double heading) {
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.heading = heading;
        calculateComponents();
    }

    private void calculateComponents() {
        headWind = Math.cos(((double) windDirection - heading)) * windSpeed;
        crossWind = Math.sin(((double) windDirection) - heading) * windSpeed;
    }

    public double getHeadWind() {
        return headWind;
    }

    public double getCrossWind() {
        return crossWind;
    }
}
