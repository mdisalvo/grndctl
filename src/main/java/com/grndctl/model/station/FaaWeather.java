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
package com.grndctl.model.station;

import javax.xml.bind.annotation.*;

/**
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"visibility", "weather", "meta", "temp","wind"})
@XmlRootElement(name = "weather")
public class FaaWeather {

    @XmlElement
    private int visibility;
    @XmlElement
    private String weather;
    @XmlElement
    private FaaWeatherMeta meta;
    @XmlElement
    private String temp;
    @XmlElement
    private String wind;

    public FaaWeather(){ }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public FaaWeatherMeta getMeta() {
        return meta;
    }

    public void setMeta(FaaWeatherMeta meta) {
        this.meta = meta;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "weather: {" +
                "visibility=" + visibility +
                ", weather='" + weather + '\'' +
                ", meta=" + meta +
                ", temp='" + temp + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }
}
