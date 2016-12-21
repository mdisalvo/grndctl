/**
 * MIT License
 *
 * Copyright (c) 2016 grndctl
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
