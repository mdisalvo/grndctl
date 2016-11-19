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
 *       &lt;attribute name="min_ft_msl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="max_ft_msl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "altitude")
public class Altitude {

    @XmlAttribute(name = "min_ft_msl")
    protected Integer minFtMsl;
    @XmlAttribute(name = "max_ft_msl")
    protected Integer maxFtMsl;

    /**
     * Gets the value of the minFtMsl property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getMinFtMsl() {
        return minFtMsl;
    }

    /**
     * Sets the value of the minFtMsl property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setMinFtMsl(Integer value) {
        this.minFtMsl = value;
    }

    /**
     * Gets the value of the maxFtMsl property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getMaxFtMsl() {
        return maxFtMsl;
    }

    /**
     * Sets the value of the maxFtMsl property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setMaxFtMsl(Integer value) {
        this.maxFtMsl = value;
    }

}
