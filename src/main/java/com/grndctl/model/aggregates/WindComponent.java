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
