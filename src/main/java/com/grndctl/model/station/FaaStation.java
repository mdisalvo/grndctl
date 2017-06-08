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
