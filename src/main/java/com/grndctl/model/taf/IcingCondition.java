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
// Generated on: 2014.12.06 at 02:18:46 PM EST 
//

package com.grndctl.model.taf;

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
 *       &lt;attribute name="icing_intensity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="icing_min_alt_ft_agl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="icing_max_alt_ft_agl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "icing_condition")
public class IcingCondition {

    @XmlAttribute(name = "icing_intensity")
    protected String icingIntensity;
    @XmlAttribute(name = "icing_min_alt_ft_agl")
    protected Integer icingMinAltFtAgl;
    @XmlAttribute(name = "icing_max_alt_ft_agl")
    protected Integer icingMaxAltFtAgl;

    /**
     * Gets the value of the icingIntensity property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIcingIntensity() {
        return icingIntensity;
    }

    /**
     * Sets the value of the icingIntensity property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIcingIntensity(String value) {
        this.icingIntensity = value;
    }

    /**
     * Gets the value of the icingMinAltFtAgl property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getIcingMinAltFtAgl() {
        return icingMinAltFtAgl;
    }

    /**
     * Sets the value of the icingMinAltFtAgl property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setIcingMinAltFtAgl(Integer value) {
        this.icingMinAltFtAgl = value;
    }

    /**
     * Gets the value of the icingMaxAltFtAgl property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getIcingMaxAltFtAgl() {
        return icingMaxAltFtAgl;
    }

    /**
     * Sets the value of the icingMaxAltFtAgl property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setIcingMaxAltFtAgl(Integer value) {
        this.icingMaxAltFtAgl = value;
    }

}
