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
