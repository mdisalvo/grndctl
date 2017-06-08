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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.06 at 02:16:35 PM EST 
//

package com.grndctl.model.airsigmet;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}request_index"/>
 *         &lt;element ref="{}data_source"/>
 *         &lt;element ref="{}request"/>
 *         &lt;element ref="{}errors"/>
 *         &lt;element ref="{}warnings"/>
 *         &lt;element ref="{}time_taken_ms"/>
 *         &lt;element ref="{}data"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" default="1.1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "requestIndex", "dataSource", "request",
        "errors", "warnings", "timeTakenMs", "data" })
@XmlRootElement(name = "response")
public class Response {

    @XmlElement(name = "request_index")
    protected int requestIndex;
    @XmlElement(name = "data_source", required = true)
    protected DataSource dataSource;
    @XmlElement(required = true)
    protected Request request;
    @XmlElement(required = true)
    protected Errors errors;
    @XmlElement(required = true)
    protected Warnings warnings;
    @XmlElement(name = "time_taken_ms")
    protected int timeTakenMs;
    @XmlElement(required = true)
    protected Data data;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the requestIndex property.
     * 
     */
    public int getRequestIndex() {
        return requestIndex;
    }

    /**
     * Sets the value of the requestIndex property.
     * 
     */
    public void setRequestIndex(int value) {
        this.requestIndex = value;
    }

    /**
     * Gets the value of the dataSource property.
     * 
     * @return possible object is {@link DataSource }
     * 
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Sets the value of the dataSource property.
     * 
     * @param value
     *            allowed object is {@link DataSource }
     * 
     */
    public void setDataSource(DataSource value) {
        this.dataSource = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return possible object is {@link Request }
     * 
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *            allowed object is {@link Request }
     * 
     */
    public void setRequest(Request value) {
        this.request = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return possible object is {@link Errors }
     * 
     */
    public Errors getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *            allowed object is {@link Errors }
     * 
     */
    public void setErrors(Errors value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return possible object is {@link Warnings }
     * 
     */
    public Warnings getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *            allowed object is {@link Warnings }
     * 
     */
    public void setWarnings(Warnings value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the timeTakenMs property.
     * 
     */
    public int getTimeTakenMs() {
        return timeTakenMs;
    }

    /**
     * Sets the value of the timeTakenMs property.
     * 
     */
    public void setTimeTakenMs(int value) {
        this.timeTakenMs = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return possible object is {@link Data }
     * 
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *            allowed object is {@link Data }
     * 
     */
    public void setData(Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVersion() {
        if (version == null) {
            return "1.1";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
