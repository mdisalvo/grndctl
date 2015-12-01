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
@XmlType(propOrder = {"credit", "updated", "url"})
@XmlRootElement(name = "meta")
public class FaaWeatherMeta {

    @XmlElement
    private String credit;
    @XmlElement
    private String updated;
    @XmlElement
    private String url;

    public FaaWeatherMeta() { }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "meta: {" +
                "credit='" + credit + '\'' +
                ", updated='" + updated + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
