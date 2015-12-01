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

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

/**
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"delay", "iata", "state", "name", "weather", "icao", "city", "status"})
@XmlRootElement(name = "faaStation")
public class FaaStation {

    @XmlElement
    private String delay;
    @JsonProperty("IATA")
    private String iata;
    @XmlElement
    private String state;
    @XmlElement
    private String name;
    @XmlElement
    private FaaWeather weather;
    @JsonProperty("ICAO")
    private String icao;
    @XmlElement
    private String city;
    @XmlElement
    private FaaStatus status;

    public FaaStation(){ }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FaaWeather getWeather() {
        return weather;
    }

    public void setWeather(FaaWeather weather) {
        this.weather = weather;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public FaaStatus getStatus() {
        return status;
    }

    public void setStatus(FaaStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "faaStation: {" +
                "delay='" + delay + '\'' +
                ", iata='" + iata + '\'' +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", weather=" + weather +
                ", icao='" + icao + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                '}';
    }
}
