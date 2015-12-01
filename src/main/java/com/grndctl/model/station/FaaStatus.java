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
@XmlType(propOrder = {"reason", "closureBegin", "endTime", "minDelay", "avgDelay", "maxDelay", "closureEnd", "trend", "type"})
@XmlRootElement(name = "status")
public class FaaStatus {

    @XmlElement
    private String reason;
    @XmlElement
    private String closureBegin;
    @XmlElement
    private String endTime;
    @XmlElement
    private String minDelay;
    @XmlElement
    private String avgDelay;
    @XmlElement
    private String maxDelay;
    @XmlElement
    private String closureEnd;
    @XmlElement
    private String trend;
    @XmlElement
    private String type;

    public FaaStatus() { }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClosureBegin() {
        return closureBegin;
    }

    public void setClosureBegin(String closureBegin) {
        this.closureBegin = closureBegin;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(String minDelay) {
        this.minDelay = minDelay;
    }

    public String getAvgDelay() {
        return avgDelay;
    }

    public void setAvgDelay(String avgDelay) {
        this.avgDelay = avgDelay;
    }

    public String getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(String maxDelay) {
        this.maxDelay = maxDelay;
    }

    public String getClosureEnd() {
        return closureEnd;
    }

    public void setClosureEnd(String closureEnd) {
        this.closureEnd = closureEnd;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "status: {" +
                "reason='" + reason + '\'' +
                ", closureBegin='" + closureBegin + '\'' +
                ", endTime='" + endTime + '\'' +
                ", minDelay='" + minDelay + '\'' +
                ", avgDelay='" + avgDelay + '\'' +
                ", maxDelay='" + maxDelay + '\'' +
                ", closureEnd='" + closureEnd + '\'' +
                ", trend='" + trend + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
