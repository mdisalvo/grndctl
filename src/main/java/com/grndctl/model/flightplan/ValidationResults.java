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
package com.grndctl.model.flightplan;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Validation results for an ICAO flight plan string.  Still adapting from GroundControl.
 * </p>
 *
 * @author Michael Di Salvo
 */
@XmlType(propOrder = { "flightPlan", "messages" })
@XmlRootElement(name = "validationResults")
public class ValidationResults {

    private String flightPlan;
    private String messages;

    public ValidationResults() { }

    public ValidationResults(String flightPlan, String results) {
        this.flightPlan = flightPlan;
        this.messages = results;
    }

    @XmlElement
    public String getFlightPlan() {
        return flightPlan;
    }

    @XmlElement
    public String getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ValidationResults{" +
                "flightPlan='" + flightPlan + '\'' +
                ", messages='" + messages + '\'' +
                '}';
    }

}
