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

    public WindComponent() { }

    public WindComponent(final double windSpeed, final double windDirection, final double heading) {
        calculateComponents(windSpeed, windDirection, heading);
    }

    private void calculateComponents(double windSpeed, double windDirection, double heading) {
        headWind = Math.cos(((windDirection * Math.PI) / 180) - ((heading * Math.PI) / 180)) * windSpeed;
        crossWind = Math.sin(((windDirection * Math.PI) / 180) - ((heading * Math.PI) / 180)) * windSpeed;
    }

    public double getHeadWind() {
        return headWind;
    }

    public void setHeadWind(double headWind) {
        this.headWind = headWind;
    }

    public double getCrossWind() {
        return crossWind;
    }

    public void setCrossWind(double crossWind) {
        this.crossWind = crossWind;
    }
}
